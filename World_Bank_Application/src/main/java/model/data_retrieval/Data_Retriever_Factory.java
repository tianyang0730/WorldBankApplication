package model.data_retrieval;

public class Data_Retriever_Factory {

    public static final int RETRIEVER_TYPE_WORLD_BANK = 0;

    public static Data_Retriever create_retriever(int type) {
        if (type == RETRIEVER_TYPE_WORLD_BANK) {
            return new World_Bank_Data_Retriever();
        }
        return null;
    }

}
