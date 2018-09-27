import io.javalin.Javalin;

public class MyApp {

  public static void main(String[] args) {
    Javalin app = Javalin.create().start(7000);
    app.post("/register", ctx -> ctx.status(201));
    app.patch("/topup", ctx -> ctx.status(202));
  }

}
