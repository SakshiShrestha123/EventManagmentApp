package com.example.eventmanagement;



public class Event {
    private String selectedEvent;
    private String selectedServices;
    private String selectedHotel;
   // private String eventId;
    private String eventName;
    private String numberOfGuests;
    private String entryDate;
    private String exitDate;



    // Required empty constructor for Firebase
    public Event() {

    }

    public Event(String selectedEvent, String selectedServices, String selectedHotel, String eventName, String numberOfGuests, String entryDate, String exitDate) {
        this.selectedEvent = selectedEvent;
        this.selectedServices = selectedServices;
        this.selectedHotel = selectedHotel;
    //    this.eventId = eventId;
        this.eventName = eventName;
        this.numberOfGuests = numberOfGuests;
        this.entryDate = entryDate;
        this.exitDate = exitDate;



    }

//   public Event(String eventId, String eventName, String numberOfGuests, String entryDate, String exitDate) {
//        this.eventId = eventId;
//        this.eventName = eventName;
//        this.numberOfGuests = numberOfGuests;
//        this.entryDate = entryDate;
//        this.exitDate = exitDate;
//
//    }

    public String getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(String selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public String getSelectedServices() {
        return selectedServices;
    }

    public void setSelectedServices(String selectedServices) {
        this.selectedServices = selectedServices;
    }

    public String getSelectedHotel() {
        return selectedHotel;
    }

    public void setSelectedHotel(String selectedHotel) {
        this.selectedHotel = selectedHotel;
    }

  //  public String getEventId() {
     //   return eventId;
//    }

    public String getEventName() {
        return eventName;
    }

    public String getNumberOfGuests() {
        return numberOfGuests;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getExitDate() {
        return exitDate;
    }


}