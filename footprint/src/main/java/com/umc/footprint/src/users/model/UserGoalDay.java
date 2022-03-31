package com.umc.footprint.src.users.model;

import lombok.*;

@Getter
@NoArgsConstructor
public class UserGoalDay {
    private boolean sun;
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;

    @Builder
    public UserGoalDay(boolean sun, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat){
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
    }
}
