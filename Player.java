import java.util.ArrayList;

public class Player {

    private static String[] suits = {"S", "C", "H", "D"};
    private static int numPlayers = 1;
    private ArrayList<Card> hand;
    private int playerNum;
    private int handValue;
    private String holeCards;
    private String handRankName;

    public Player() {
        hand = new ArrayList<>();
        playerNum = numPlayers++;
        handValue = 0;
        holeCards = "";
        handRankName = "None";
    }
    
    /**
     * See instructions in Schoology
     */
    public void showdown() {
        selectionSort(hand);
        ArrayList<Card> arr = new ArrayList<Card>();
        ArrayList<Card> straight = new ArrayList<Card>();
        ArrayList<Card> pairs = new ArrayList<Card>();
        ArrayList<Card> flush = new ArrayList<Card>();

        int care = 0;
        int counter = 0;
        if(mostAmount(hand) >= 5)
        {
            for(int i = 0; i < hand.size()-1; i++)
            {
                if(hand.get(i).getSuit().equals(mostSuit(hand)) && hand.get(i+1).getValue() == hand.get(i).getValue()-1)
                {
                    arr.add(hand.get(i));
                    care = i;
                    
                }
                else if(counter == 2)
                {
                    if(i+3 < hand.size() && hand.get(i+3).getSuit().equals(mostSuit(hand)) && hand.get(i+3).getValue() == hand.get(i).getValue()-1)
                    {
                        arr.add(hand.get(i));
                        care = i;
                    }
                }
                else if(arr.size() == 4)
                {
                    break;
                }
                else if(hand.get(i+1).getValue() == hand.get(i).getValue())
                {
                    if(hand.get(i).getSuit().equals(mostSuit(hand)))
                    {
                        arr.add(hand.get(i));
                        care = i;
                    }
                    i++;
                    i--;
                    counter++;
                }
                else
                {
                    arr.clear();
                }
            }
        }

        if(care+1 < 7 && hand.get(care+1).getValue() == hand.get(care).getValue()-1 && arr.size() == 4 && hand.get(care+1).getSuit().equals(mostSuit(hand)))
        {
            arr.add(hand.get(care+1));
        }
        else if(care+2 < 7 && hand.get(care+2).getValue() == hand.get(care).getValue()-1 && arr.size() == 4 && hand.get(care+2).getSuit().equals(mostSuit(hand)))
        {
            arr.add(hand.get(care+2));
        }
        else if(care+3 < 7 && hand.get(care+3).getValue() == hand.get(care).getValue()-1 && arr.size() == 4 && hand.get(care+3).getSuit().equals(mostSuit(hand)))
        {
            arr.add(hand.get(care+3));
        }
        if(arr.size() >= 5 && consecutively(hand) >= 4) //straight flush
        {
            for(int p = 0; p < 5; p++)
            {
                hand.set(p, arr.get(p));
            }
            hand.remove(6);
            hand.remove(5);
        }
        else if(highestCount(hand) == 3){ //four of a kind
            for(int k = 0; k < hand.size(); k++)
            {
                if(mostValues(hand) == hand.get(k).getValue())
                {
                    Card temp = hand.get(k);
                    hand.remove(k);
                    hand.add(0, temp);
                }
            }
            hand.remove(6);
            hand.remove(5);
        }
        else if(highestCount(hand) == 2) //full house
        {
            for(int k = 0; k < hand.size(); k++)
            {
                if(mostValues(hand) == hand.get(k).getValue())
                {
                    Card temp = hand.get(k);
                    hand.remove(k);
                    hand.add(0, temp);
                }
            }
            if(hand.get(3).getValue() == hand.get(4).getValue())
            {
                hand.remove(6);
                hand.remove(5);
            }
            else if(hand.get(5).getValue() == hand.get(6).getValue())
            {
                hand.remove(4);
                hand.remove(3);
            }
            else if(hand.get(4).getValue() == hand.get(5).getValue())
            {
                hand.remove(6);
                hand.remove(3);
            }
        }
        else if(mostAmount(hand) >= 5) //flush
        {
            for(int p = 0; p < hand.size(); p++)
            {
                if(hand.get(p).getSuit().equals(mostSuit(hand)))
                {
                    flush.add(hand.get(p));
                }
            }
            for(int m = 0; m < 5; m++)
            {
                hand.set(m, flush.get(m));
            }
            hand.remove(6);
            hand.remove(5);
        }
        if(consecutively(hand) >= 4 && hand.size() == 7) //straight
        {
            int count = 0;
            int c = 0;
            for(int i = 0; i < hand.size()-1; i++)
            {
                if(hand.get(i+1).getValue() == hand.get(i).getValue()-1)
                {
                    straight.add(hand.get(i));
                    count = i;
                }
                else if(c == 2)
                {
                    if(i+3 < hand.size() && hand.get(i+3).getSuit().equals(mostSuit(hand)) && hand.get(i+3).getValue() == hand.get(i).getValue()-1)
                    {
                        straight.add(hand.get(i));
                        count = i;
                    }
                }
                else if(straight.size() == 4)
                {
                    break;
                }
                else if(hand.get(i+1).getValue() == hand.get(i).getValue())
                {
                    i++;
                    i--;
                    c++;
                }
                else
                {
                    straight.clear();
                }
            }
            if(count+1 < 7 && hand.get(count+1).getValue() == hand.get(count).getValue()-1 && straight.size() == 4)
            {
                straight.add(hand.get(count+1));
            }
            for(int p = 0; p < 5; p++)
            {
                if(straight.size() >= 5)
                {
                    hand.set(p, straight.get(p));
                }
            }
            hand.remove(6);
            hand.remove(5);
        }
        if(highestCount(hand) == 2 && hand.size() == 7)
        {
            hand.remove(6);
            hand.remove(5);
        }
        else if(highestCount(hand) == 1 && hand.size() == 7) //Two pairs
        {
            for(int i = 0; i < hand.size(); i++)
            {
                if(mostValues(hand) == hand.get(i).getValue())
                {
                    Card ob = hand.get(i);
                    hand.remove(i);
                    hand.add(0, ob);
                }
                for(int j = 0; j < hand.size(); j++)
                {
                    if(hand.get(i).getValue() == hand.get(j).getValue() && i != j && mostValues(hand) != hand.get(i).getValue())
                    {
                        pairs.add(hand.get(i));
                        pairs.add(hand.get(j));
                        break;
                    
                    }
                }
            }
            if(pairs.size() >= 2)
            {
                int index = 0;
                int otherIndex = 0;
                for(int l = 0; l < hand.size(); l++)
                {
                    if(pairs.get(0) == hand.get(l))
                    {
                        index = l;
                    }
                    if(pairs.get(1) == hand.get(l))
                    {
                        otherIndex = l;
                    }
                }
                Card obj = hand.get(index);
                hand.remove(index);
                hand.add(2, obj);
                Card one = hand.get(otherIndex);
                hand.remove(otherIndex);
                hand.add(2, one);
            }
            hand.remove(6);
            hand.remove(5);
        }
        else if(hand.size() == 7)
        {
            hand.remove(6);
            hand.remove(5);
        }
        

        int first = checkForBestHand(hand);
        int val = setHandValue(hand);
        handValue = first + val;
        if(first == 9999999)
        {
            handRankName = "Royal Flush";
        }
        else if(first >= 9000000)
        {
            handRankName = "Straight Flush";
        }
        else if(first >= 8000000)
        {
            handRankName = "Four of a Kind";
        }
        else if(first >= 7000000)
        {
            handRankName = "Full House";
        }
        else if(first >= 6000000)
        {
            handRankName = "Flush";
        }
        else if(first >= 5000000)
        {
            handRankName = "Straight";
        }
        else if(first >= 4000000)
        {
            handRankName = "Three of a Kind";
        }
        else if(first >= 3000000)
        {
            handRankName = "Two Pairs";
        }
        else if(first >= 2000000)
        {
            handRankName = "Pair";
        }
        else
        {
            handRankName = "High Card";
        }
    }
    
    //Finds the most number of values in an arraylist
    public int mostValues(ArrayList<Card> bhand) 
    {
        int max = -1;
        int element = -1;
        for(int i = 0; i < bhand.size(); i++)
        {
            int count = 0;
            for(int j = 0; j < bhand.size(); j++)
            {
                if(bhand.get(i).getValue() == bhand.get(j).getValue() && i != j)
                {
                    count++;
                }
            }
            if(count > element)
            {
                max = bhand.get(i).getValue();
                element = count;
            }
        }
        return max;
    }
    
    //Gets the highest count of the most number of values in an arraylist
    public int highestCount(ArrayList<Card> band)
    {
        int max = -1;
        int element = -1;
        int maxCount = -1;
        for(int i = 0; i < band.size(); i++)
        {
            int count = 0;
            for(int j = 0; j < band.size(); j++)
            {
                if(band.get(i).getValue() == band.get(j).getValue() && i != j)
                {
                    count++;
                }
            }
            if(count > element)
            {
                max = band.get(i).getValue();
                maxCount = count;
                element = count;
            }
        }
        return maxCount;
    }
    
    //Finds which suit is most abundant in an arraylist
    public String mostSuit(ArrayList<Card> aHand)
    {
        int spades = 0;
        int clovers = 0;
        int hearts = 0;
        int diamonds = 0;
        
        for(int i = 0; i < aHand.size(); i++)
        {
            if(aHand.get(i).getSuit().equals("S"))
            {
                spades++;
            }
            else if(aHand.get(i).getSuit().equals("C"))
            {
                clovers++;
            }
            else if(aHand.get(i).getSuit().equals("H"))
            {
                hearts++;
            }
            else if(aHand.get(i).getSuit().equals("D"))
            {
                diamonds++;
            }
        }
        
        if(spades > clovers && spades > diamonds && spades > hearts)
        {
            return "S";
        }
        else if(clovers > spades && clovers > diamonds && clovers > hearts)
        {
            return "C";
        }
        else if(hearts > clovers && hearts > diamonds && hearts > spades)
        {
            return "H";
        }
        else if(diamonds > clovers && diamonds > spades && diamonds > hearts)
        {
            return "D";
        }
        
        return "none";
    }
    
    //Finds the most number of Suits in an arraylist
    public int mostAmount(ArrayList<Card> aHand)
    {
        int spades = 0;
        int clovers = 0;
        int hearts = 0;
        int diamonds = 0;
        
        for(int i = 0; i < aHand.size(); i++)
        {
            if(aHand.get(i).getSuit().equals("S"))
            {
                spades++;
            }
            else if(aHand.get(i).getSuit().equals("C"))
            {
                clovers++; 
            }
            else if(aHand.get(i).getSuit().equals("H"))
            {
                hearts++;
            }
            else if(aHand.get(i).getSuit().equals("D"))
            {
                diamonds++;
            }
        }
        if(spades > clovers && spades > diamonds && spades > hearts)
        {
            return spades;
        }
        else if(clovers > spades && clovers > diamonds && clovers > hearts)
        {
            return clovers;
        }
        else if(hearts > clovers && hearts > diamonds && hearts > spades)
        {
            return hearts;
        }
        else if(diamonds > clovers && diamonds > spades && diamonds > hearts)
        {
            return diamonds;
        }
        
        return -1;
    }
    
    //Checks if numbers are consecutive or not
    public int consecutively(ArrayList<Card> anotherHand)
    {
        int z = 0;
        int count = 0;
        for(int i = 0; i < anotherHand.size()-1; i++)
        {
            if(anotherHand.get(i+1).getValue() == anotherHand.get(i).getValue()-1)
            {
                z++;
            }
            else if(count == 2)
            {
                if(i+3 < anotherHand.size() && anotherHand.get(i+3).getSuit().equals(mostSuit(hand)) && anotherHand.get(i+3).getValue() == anotherHand.get(i).getValue()-1)
                {
                    z++;
                }
            }
            else if(z == 4)
            {
                break;
            }
            else if(anotherHand.get(i+1).getValue() == anotherHand.get(i).getValue())
            {
                i++;
                i--;
                count++;
            }
            else if(z > 0 && anotherHand.get(i+1).getValue() != anotherHand.get(i).getValue())
            {
                z--;
            }
        }
        return z;
    }
    
    
    //Assigns value to corresponding hand
    public int checkForBestHand(ArrayList<Card> fullHand){ 
        int count = 0;
        int other = 0;
        int finalOne = 0;
        int another = 0;
        int a = 0;
        int b = 0;
        if(fullHand.get(0).getValue() == 14)
        {
            for(int i = 0; i < fullHand.size()-1; i++)
            {
                if(fullHand.get(i + 1).getValue() == fullHand.get(i).getValue() - 1 && fullHand.get(i+1).getSuit() == fullHand.get(i).getSuit())
                {
                    count++;
                }
            }
            if(count >= 4)
            {
                return 9999999; //royal flush
            }
        }
        if(fullHand.get(0).getValue() != 14)
        {
            for(int j = 0; j < fullHand.size()-1; j++)
            {
                if(fullHand.get(j + 1).getValue() == fullHand.get(j).getValue() - 1 && fullHand.get(j+1).getSuit() == fullHand.get(j).getSuit())
                {
                    other++;
                }
            }
            if(other >= 4)
            {
                return 9000000; //straight flush
            }
        }
        if(fullHand.get(1).getValue() == fullHand.get(0).getValue()-1)
        {
            for(int j = 0; j < fullHand.size()-1; j++)
            {
                if(fullHand.get(j+1).getValue() == fullHand.get(j).getValue()-1)
                {
                    b++;
                }
            }
            if(b >= 4)
            {
                return 5000000; //straight
            }
        }
        if(fullHand.get(0).getValue() == fullHand.get(1).getValue())
        {
            for(int l = 0; l < fullHand.size()-1; l++)
            {
                if(fullHand.get(l + 1).getValue() == fullHand.get(l).getValue() && fullHand.get(l + 1).getValue() == fullHand.get(0).getValue())
                {
                    another++;
                }
                else if(fullHand.get(l + 1).getValue() == fullHand.get(l).getValue())
                {
                    a++;
                }
            }
            if(another == 3)
            {
                return 8000000; //four of a kind
            }
            else if(another == 2 && a == 1)
            {
                return 7000000; //full house
            }
            else if(another == 2)
            {
                return 4000000; //three of a kind
            }
            else if(another == 1 && a == 1)
            {
                return 3000000; //two pairs
            }
            else if(another == 1)
            {
                return 2000000; //pair
            }
        }
        if(fullHand.get(0).getSuit() == fullHand.get(1).getSuit())
        {
            for(int k = 0; k < fullHand.size()-1; k++)
            {
                if(fullHand.get(k+1).getSuit() == fullHand.get(k).getSuit())
                {
                    finalOne++;
                }
            }
            if(finalOne >= 4)
            {
                return 6000000; //flush
            }
        }
        return 1000000;
    }
    
    //Assigns value to each card based on position in the hand arraylist
    public int setHandValue(ArrayList<Card> getHandValue){
        //Check code
        int sum = 0;
        int one = 10000;
        for(int i = 0; i < hand.size(); i++)
        {
            for(int j = 2; j < 15; j++)
            {
                if(hand.get(i).getValue() == j && j != 2 && checkForBestHand(hand) != 9999999)
                {
                    sum += ((j-1) * one);
                }
            }
            if(hand.get(i).getValue() == 2 && i != hand.size()-1 && checkForBestHand(hand) != 9999999)
            {
                sum += (1.5 * one);
            }
            else if(hand.get(i).getValue() == 2 && i == hand.size() - 1 && checkForBestHand(hand) != 9999999)
            {
                sum += 1;
            }
            one/=10;
        }
        
        return sum;
    }
    
    //Sorts cards from greatest to least
    public ArrayList<Card> selectionSort(ArrayList<Card> sorted)
    {
        int count = 0;
        int currentMaxIndex;
        for (int i = 0; i < sorted.size() - 1; i++)
        {
            currentMaxIndex = i;
            for (int j = i + 1; j < sorted.size(); j++)
            {
                if(sorted.get(j).getValue() > sorted.get(currentMaxIndex).getValue())
                {
                    currentMaxIndex = j;
                }
            }
            if (i != currentMaxIndex)
            {
                Card temp = sorted.get(currentMaxIndex);
                sorted.set(currentMaxIndex, sorted.get(i));
                sorted.set(i, sorted.get(currentMaxIndex));
                count++;
            }
        }
        return sorted;
    }
    
    
    

    public void addCard(Card card) {
        //insert cards into hand in sorted order
        int i = 0;
        while (i < hand.size() && hand.get(i).compareTo(card) > 0) {
            i++;
        }
        hand.add(i, card);

        // save string output of the private cards (first 2 cards dealt).
        // This is needed for ouput in the toString() method.
        if (hand.size() == 2) {
            holeCards = hand.toString();
        }
    }
    
    public int getHandValue() {
        return handValue;
    }

    public String getName() {
        return String.format("Player%3d", playerNum);
    }

    public String getHandRankName() {
        return handRankName;
    }

    @Override
    public String toString() {
        return String.format("%-8s = %-7d  %-10s  %-24s  %s",
            getName(), handValue, holeCards, hand, handRankName);
       
    }
}