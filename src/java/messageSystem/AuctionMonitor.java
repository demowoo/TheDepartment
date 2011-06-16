/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messageSystem;

import ConnectionDataBase.AuctionhistoryHelper;
import ConnectionDataBase.MessageSystemHelper;
import ConnectionDataBase.Messagesystem;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author root
 */
public class AuctionMonitor {

    private PriorityQueue<Auction> currentAuctions = new PriorityQueue<Auction>();
    private List<Auction> listAuction = new ArrayList<Auction>();
    private MessageSystemHelper messageSystemHelper = new MessageSystemHelper();
    private AuctionhistoryHelper auctionHistoryHelper = new AuctionhistoryHelper();

    public AuctionMonitor() {
        List<Messagesystem> auctionsDb =
                messageSystemHelper.getMessages(MessageSingleton.AUCTION_BOARD, MessageSingleton.AUCTION_OFFER);

        Set<Auction> auctionsToRemove = new HashSet<Auction>();
        Date currentDate = new Date();

        Iterator<Messagesystem> iterator = auctionsDb.iterator();

        while (iterator.hasNext()) {
            Messagesystem auctionOffer = iterator.next();

            Auction auction = new Auction();
            auction = auction.parseAuction(auctionOffer.getMsg());

            auction.setMsgnumber(auctionOffer.getMsgnumber());
            auction.setSenderid(auctionOffer.getSenderid());
            auction.setSubject(auctionOffer.getSubject());
            auction.setCreationtime(auctionOffer.getCreationtime());

            AuctionhistoryHelper auctionhistoryHelper = new AuctionhistoryHelper();
            auctionhistoryHelper.getHighestAuctionOffer(auction.getMsgnumber(),auction);

            if (auction.getExpireDate().compareTo(currentDate) <= 0) {
                auctionsToRemove.add(auction);
            } else {
                currentAuctions.add(auction);
                listAuction.add(auction);
            }
        }

        Iterator<Auction> auctionIterator = auctionsToRemove.iterator();
        while (iterator.hasNext()) {
            Auction auction = auctionIterator.next();

            messageSystemHelper.deleteMsg(auction.getMsgnumber());
            auctionHistoryHelper.deleteOffers(auction.getMsgnumber());

            auction.finishAuction();
        }
    }

    public synchronized void addAuction(Auction auction) {
        currentAuctions.add(auction);
        listAuction.add(auction);

        int msgNumber = messageSystemHelper.createMessage(auction.getSenderid(),
                MessageSingleton.AUCTION_BOARD, auction.getSubject(),
                auction.encode(), MessageSingleton.AUCTION_OFFER);

        auction.setMsgnumber(msgNumber);
    }

    public synchronized void update() {
        Date currentDate = new Date();

        while (!currentAuctions.isEmpty()) {
            Auction auction = currentAuctions.peek();
            if (auction.getExpireDate().compareTo(currentDate) <= 0) {
                currentAuctions.remove(auction);
                listAuction.remove(auction);

                messageSystemHelper.deleteMsg(auction.getMsgnumber());

                auction.finishAuction();
            } else {
                return;
            }
        }
    }

    public synchronized List<Auction> getAuctionList() {
        return listAuction;
    }

    public boolean placeOffer(Auction auction, int offer) {
        auction.setHighestOfferedPrice(offer);
        return true;
    }
}
