import com.acefet.blog.constant.enums.UserStatus;
import com.acefet.blog.util.Util;

import java.util.UUID;

public class Test {

    public static void main(String[] args) {
//        UserStatus[] statuses = UserStatus.values();
//        for(UserStatus userStatus : statuses){
//            System.out.println(userStatus.getStatusCode()+"---"+userStatus.getStatusName());
//        }
        String str = Util.generateShortUuid();
        System.out.println(str+"\n"+Util.checkShortId(str));
    }

}
