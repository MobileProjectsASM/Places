package com.applications.asm.domain.entities;

public class Hours {
    private Hour openHour;
    private Hour closeHour;

    public Hours(Hour openHour, Hour closeHour) {
        this.openHour = openHour;
        this.closeHour = closeHour;
    }

    public Hour getOpenHour() {
        return openHour;
    }

    public void setOpenHour(Hour openHour) {
        this.openHour = openHour;
    }

    public Hour getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(Hour closeHour) {
        this.closeHour = closeHour;
    }
}
