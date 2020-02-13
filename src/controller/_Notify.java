package controller;

public class _Notify {
    private DatabaseAccessObject dao;
    private String query;

//    public void checkCount(String depatment,int counts){
//        switch (depatment){
//            case 1:
////                if(counts == 3){
////
////                }
//                break;
//            case 2:
//                break;
//            case 3:
//                break;
//            case 4:
//                break;
//        }
//    }

    public void notifyInsert(String student_key,String description,int department_key){
        query = "insert into notification_tbl (`studentNumber`,`description`,`status`,`department_key`) VALUES ("+student_key+",'"+description+"','unread',"+department_key+")";
        try {
            dao.saveData(query);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
