package paul.TextQuest.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Created by Paul Dennis on 8/8/2017.
 */



@JsonTypeInfo(defaultImpl=BackpackItem.class,
        use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Note.class, name = "note")
})
@JsonInclude(Include.NON_NULL)
//@JsonInclude(Include.NON_DEFAULT)
public class BackpackItem extends DungeonRoomEntity {

    private String name;
    
    
    private Boolean isQuestItem;
    private int value;
    
    private String onPickup;
    private boolean darklight; //Item can only be seen in the dark

    public BackpackItem () {

    }

    public BackpackItem (String name) {
        this.name = name;
        isQuestItem = false;
    }

    public BackpackItem(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public Boolean isQuestItem() {
        return isQuestItem;
    }

    @JsonInclude(Include.NON_DEFAULT)
    public void setQuestItem(Boolean questItem) {
        isQuestItem = questItem;
    }

    public int getValue() {
        return value;
    }

    @JsonInclude(Include.NON_DEFAULT)
    public void setValue(int value) {
        this.value = value;
    }

    public String getOnPickup() {
        return onPickup;
    }

    public void setOnPickup(String onPickup) {
        this.onPickup = onPickup;
    }

    public boolean hasPickupAction () {
        return onPickup != null;
    }

    @Override
    public String toString () {
        return name;
    }

    public static final double DEFAULT_VISIBILITY_THRESHHOLD = 0.6;
    public boolean isVisible (double lighting) {
        if (darklight) {
            return lighting == 0.0;
        }
        return lighting >= DEFAULT_VISIBILITY_THRESHHOLD;
    }

    public boolean isDarklight() {
        return darklight;
    }

    @JsonInclude(Include.NON_DEFAULT)
    public void setDarklight(boolean darklight) {
        this.darklight = darklight;
    }
}