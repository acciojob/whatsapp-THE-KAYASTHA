package com.driver;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WhatsappService {

    WhatsappRepository repoObj=new WhatsappRepository();
    public String createUser(String name, String mobile)  {

        String ans=repoObj.createUser(name,mobile);
        return ans;
    }

    public Group createGroup(List<User> users) {

        Group group =repoObj.createGroup(users);
        return group;


    }

    public int createMessage(String content) {
        int ans=repoObj.createMessage(content);
        return ans;
    }

    public int sendMessage(Message message, User sender, Group group) {

        int ans=repoObj.sendMessage(message,sender,group);

        return ans;
    }

    public String changeAdmin(User approver, User user, Group group) {

            return repoObj.changeAdmin(approver,user,group);

    }

    public int removeUser(User user) {

        return repoObj.removeUser(user);


    }

    public String findMessage(Date start, Date end, int k) {


        return repoObj.findMessage(start,end,k);
    }

    //  public Group createGroup(List<User> users) {



 //   }
}
