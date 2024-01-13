package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap; // group hashMap;

    private HashMap<Integer,Message> messageHashMap=new HashMap<>();
    private HashMap<String,String> userHashMap=new HashMap<>(); //userHashMap
   // private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private static int customGroupCount=1;
    private static int messageId=1 ;

    public WhatsappRepository(){
      //git   this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 1;
        this.messageId = 1;
    }

    public String createUser(String name, String mobile)  {
        if(userMobile.contains(mobile)){
                return "User already exists";
        }

        userMobile.add(mobile);
        userHashMap.put(name,mobile);
        return "SUCCESS";


    }

    public Group createGroup(List<User> users) {
        if(users.size()==2){
                Group temp=new Group(users.get(1).getName(),users.size());
                groupUserMap.put(temp,users);
                return temp;
        }

        Group temp=new Group("Group "+customGroupCount++,users.size());

        groupUserMap.put(temp,users);
        return temp;




    }

    public int createMessage(String content) {

        Message temp=new Message(content);
            temp.setId(messageId);
            messageHashMap.put(messageId,temp);

            return messageId++;

    }

    public int sendMessage(Message message, User sender, Group group) {
        if(!groupUserMap.containsKey(group)){
            return -1;
        }
        if(!groupUserMap.get(group).contains(sender) ){
            return -2;
        }
            message.setId(messageId);
         messageHashMap.put(messageId++,message);
            return messageHashMap.size();


    }

    public String changeAdmin(User approver, User user, Group group) {
        if(!groupUserMap.containsKey(group)){
            return "Group does not exist";
        }
        if(groupUserMap.get(group).get(0)!=approver){
            return "Approver does not have rights";
        }
        if(!groupUserMap.get(group).contains(user)){
            return "User is not a participant";
        }

        groupUserMap.get(group).remove(user);
        groupUserMap.get(group).add(0,user);
        return "SUCCESS";


    }
}
