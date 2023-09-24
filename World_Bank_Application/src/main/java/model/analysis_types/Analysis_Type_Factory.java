package model.analysis_types;

public class Analysis_Type_Factory {

    public static Analysis_Type create_analysis(int type) {
        if (type == 1) 
            return new Analysis_Type_1();
        else if (type == 2) 
            return new Analysis_Type_2();
        else if (type == 3) 
            return new Analysis_Type_3();
        else if (type == 4) 
            return new Analysis_Type_4();

        return null;
    }

}