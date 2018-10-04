package Controller;

import Model.Model;
import Model.JdbcModel;
import View.View;
import View.GsonView;
import io.javalin.Javalin;

public class MyApp {

  public static void main(String[] args) {

    Javalin app = Javalin.create().start(7000);

    View gson = new GsonView();
    Model jdbc = new JdbcModel();

    Registration registration = new Registration(gson, jdbc);
    Edit edit = new Edit(gson, jdbc);
    Detail detail = new Detail(gson, jdbc);

    app.get("/cards/", detail::handleCardList);
    app.post("/cards", registration::handleCardRegistration);
    app.get("/cards/:id", detail::handleCardDetail);
    app.put("/cards/:id", edit::handleEdit);
    app.put("/cards/credit/:id", edit::handleTopUp);

  }

}
