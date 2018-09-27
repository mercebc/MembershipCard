import com.google.gson.Gson;
import io.javalin.Context;

import java.util.ArrayList;
import java.util.List;

public class ToppingUp {

  static List<Card> cards = new ArrayList<>();

  static Card oneCard = new Card(3, "Mary");
  static Card twoCard = new Card(4, "Peter");

  public static void handleTopUp(Context context) {

    cards.add(oneCard);
    cards.add(twoCard);

    Gson gson = new Gson();

    String employerIDString = context.pathParam("employerID");
    int employerID = Integer.parseInt(employerIDString);

    Card card = gson.fromJson(context.body(), Card.class);

    for (Card myCard: cards) {
      if(myCard.getEmployerID()==employerID){
            myCard.setAmountToppingUp(card.amountToppingUp);
      }
    }

    context.status(202);

  }




}
