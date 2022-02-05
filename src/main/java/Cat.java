import com.fasterxml.jackson.annotation.JsonProperty;

public class Cat {
    private String id ;
    private String text;
    private String type;
    private String user;
    private String  upvotes;

    public Cat(
            @JsonProperty("id") String id,
            @JsonProperty("text") String text,
            @JsonProperty("type") String type,
            @JsonProperty("user") String user,
            @JsonProperty("upvotes") String upvotes

    ){
        this.id = id;
        this.text = text;
        this.type = type;
        this.upvotes = upvotes;
        this.user = user;
    }

    public String getId(){
        return  id;
    }

    public String getText(){
        return  text;
    }

    public String getType(){
        return  type;
    }

    public String getUpvotes(){
        return upvotes;
    }

    public String toString(){
        return id +"\n"+ text+"\n"+ type+"\n"+ upvotes;
    }
}
