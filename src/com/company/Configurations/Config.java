package com.company.Configurations;

public final class Config { //TODO: Следует сделать Singleton
    private static int PORT;
    private static int sendPORT;

    public static int getPORT() {
        return PORT;
    }

    public static void setPORT(int PORT) {
        Config.PORT = PORT;
    }

    public static int getSendPORT() {
        return sendPORT;
    }

    public static void setSendPORT(int sendPORT) {
        Config.sendPORT = sendPORT;
    }
}
