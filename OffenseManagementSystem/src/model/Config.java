package model;
import controller.ConnectDbController;

public class Config {
    public static String server = ConnectDbController.getConnectDbController().getDatabaseInfo().get("server");
    public static String port = ConnectDbController.getConnectDbController().getDatabaseInfo().get("port");
    public static String database = ConnectDbController.getConnectDbController().getDatabaseInfo().get("database");
    public static String username = ConnectDbController.getConnectDbController().getDatabaseInfo().get("username");
    public static String password = ConnectDbController.getConnectDbController().getDatabaseInfo().get("password");
}
