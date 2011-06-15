package messageSystem;

import ConnectionDataBase.MessageSystemHelper;
import ConnectionDataBase.Messagesystem;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;


public class NoticeMonitor {

    private PriorityQueue<TradeOffer> currentNotices = new PriorityQueue<TradeOffer>();
    private List<TradeOffer> listNotices = new ArrayList<TradeOffer>();
    private MessageSystemHelper messageSystemHelper = new MessageSystemHelper();

    public NoticeMonitor() {
        List<Messagesystem> noticesDb =
                messageSystemHelper.getMessages(MessageSingleton.NOTICE_BOARD, MessageSingleton.NOTICE_BOARD_OFFER);

        Iterator<Messagesystem> iterator = noticesDb.iterator();

       while(iterator.hasNext()) {
           Messagesystem noticeOffer = iterator.next();

           TradeOffer tradeOffer = new TradeOffer();
           tradeOffer.parse(noticeOffer.getMsg());
           tradeOffer.setSenderid(noticeOffer.getSenderid());
           tradeOffer.setSubject(noticeOffer.getSubject());
           tradeOffer.setCreationtime(noticeOffer.getCreationtime());

           currentNotices.add(tradeOffer);
           listNotices.add(tradeOffer);

       }
    }

    public synchronized void addNoticeOffer(TradeOffer noticeOffer) {
        currentNotices.add(noticeOffer);
        listNotices.add(noticeOffer);

        messageSystemHelper.createMessage(noticeOffer.getSenderid(), MessageSingleton.NOTICE_BOARD,
                noticeOffer.getSubject(),noticeOffer.encode(), MessageSingleton.NOTICE_BOARD_OFFER);
    }

    public synchronized void update() {
        Date currentDate = new Date();

        while(!currentNotices.isEmpty()) {
            TradeOffer noticeOffer = currentNotices.peek();
            if(noticeOffer.getExpireDate().compareTo(currentDate) >= 0) {
                currentNotices.poll();
                currentNotices.remove(noticeOffer);
                noticeOffer.accept();
            } else {
                return;
            }
        }
    }

    public synchronized List<TradeOffer> getNoticeOffers() {
        return listNotices;
    }

    public boolean acceptNoticeOffer(TradeOffer noticeOffer) {

        if(!currentNotices.contains(noticeOffer))
            return false;
        
        boolean result = noticeOffer.accept();

        if(result) {
            currentNotices.remove(noticeOffer);
            listNotices.remove(noticeOffer);
        }

        return result;
    }

}
