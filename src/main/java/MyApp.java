import io.javalin.Javalin;

public class MyApp {

  public static void main(String[] args) {
    Javalin app = Javalin.create().start(7000);

    app.get("/dashboard", ctx -> ctx.status(201));
    app.post("/register", ctx -> ctx.status(202));
    app.get("view/:cardID", ctx -> ctx.status(201));
    app.post("/register/:cardID", Registration::handleRegistration);
    app.put("/topup/:cardID", ToppingUp::handleTopUp);
  }

}
