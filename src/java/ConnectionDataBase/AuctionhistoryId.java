package ConnectionDataBase;
// Generated 15-Jun-2011 22:43:29 by Hibernate Tools 3.2.1.GA



/**
 * AuctionhistoryId generated by hbm2java
 */
public class AuctionhistoryId  implements java.io.Serializable {


     private int auctionid;
     private String bidder;
     private int offer;

    public AuctionhistoryId() {
    }

    public AuctionhistoryId(int auctionid, String bidder, int offer) {
       this.auctionid = auctionid;
       this.bidder = bidder;
       this.offer = offer;
    }
   
    public int getAuctionid() {
        return this.auctionid;
    }
    
    public void setAuctionid(int auctionid) {
        this.auctionid = auctionid;
    }
    public String getBidder() {
        return this.bidder;
    }
    
    public void setBidder(String bidder) {
        this.bidder = bidder;
    }
    public int getOffer() {
        return this.offer;
    }
    
    public void setOffer(int offer) {
        this.offer = offer;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AuctionhistoryId) ) return false;
		 AuctionhistoryId castOther = ( AuctionhistoryId ) other; 
         
		 return (this.getAuctionid()==castOther.getAuctionid())
 && ( (this.getBidder()==castOther.getBidder()) || ( this.getBidder()!=null && castOther.getBidder()!=null && this.getBidder().equals(castOther.getBidder()) ) )
 && (this.getOffer()==castOther.getOffer());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getAuctionid();
         result = 37 * result + ( getBidder() == null ? 0 : this.getBidder().hashCode() );
         result = 37 * result + this.getOffer();
         return result;
   }   


}


