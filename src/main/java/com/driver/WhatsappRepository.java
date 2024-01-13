package com.driver;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap; // group hashMap;

    private HashMap<Integer,Message> messageHashMap=new HashMap<>();
    private HashMap<String,String> userHashMap=new HashMap<>(); //userHashMap
   private HashMap<Group, HashMap<User,List<Message>>> groupMessageMap=new HashMap<>();


    private HashSet<String> userMobile;
    private static int customGroupCount=1;
    private static int messageId=1 ;



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

            if(groupMessageMap.containsKey(group)){
                if(groupMessageMap.get(group).containsKey(sender)){
                    groupMessageMap.get(group).get(sender).add(message);
                }
                else{
                    List<Message> mm=new ArrayList<>();
                    mm.add(message);
                    groupMessageMap.get(group).put(sender,mm);
                }
            }
            else{
                List<Message> ll=new ArrayList<>();
                ll.add(message);
                HashMap<User,List<Message>> uu=new HashMap<>();
                uu.put(sender,ll);
                groupMessageMap.put(group,uu);
            }
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

    public int removeUser(User user) {

            boolean check=false;
             Group temp=new Group();
            for(Group i:groupUserMap.keySet()){
                if(groupUserMap.get(i).contains(user)){
                    check=true;
                    temp=i;
                    break;
                }

            }
            if(!check){
                return -1;
            }
            if(groupUserMap.get(temp).get(0)==user){
                return -2;
            }
        groupUserMap.get(temp).remove(user);
        HashMap<User,List<Message>> op=groupMessageMap.get(temp);

        for(User i:op.keySet()){
             List<Message> ok=new ArrayList<>();

             for(Message j:ok){
                 messageHashMap.remove(j.getId());
             }

        }
        groupMessageMap.get(temp).remove(user);


        int noOfUserInGroup=groupUserMap.get(temp).size();
        int noOfMessageInGroup=0;



        for(User i:op.keySet()){
            noOfMessageInGroup+=op.get(i).size();

        }
        int noOfMessageOverall=0;

        for(Group i:groupMessageMap.keySet()){

            HashMap<User,List<Message>> use=groupMessageMap.get(i);

            for(User j:use.keySet()){

                noOfMessageOverall+=use.get(j).size();
            }


        }

        return noOfUserInGroup+noOfMessageInGroup+noOfMessageOverall;







    }

    public String findMessage(Date start, Date end, int k) {
        List<Message> messages = new ArrayList<>(); // You need to implement this method

        for(Integer i:messageHashMap.keySet()){

            messages.add(messageHashMap.get(i));
        }
        // Filter messages between start and end dates
        List<Message> filteredMessages = messages.stream()
                .filter(message -> message.getTimestamp().after(start) && message.getTimestamp().before(end))
                .sorted(Comparator.comparing(Message::getTimestamp).reversed()) // Sort in descending order by timestamp
                .collect(Collectors.toList());

        // Check if K is greater than the number of messages
        if (k> filteredMessages.size()) {
            return "K is greater than the number of messages";
        }

        // Get the Kth latest message
        Message kthLatestMessage = filteredMessages.get(k - 1);

        // You can then return or process the kthLatestMessage as needed
        return kthLatestMessage.getContent();

    }
}
