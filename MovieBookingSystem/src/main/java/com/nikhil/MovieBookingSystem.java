package com.nikhil;

import java.util.Collections;
import java.util.List;

public class MovieBookingSystem {
    private List<Theater> theaters;

    public MovieBookingSystem(List<Theater> theaters){
        this.theaters = theaters;
    }

    public List<Show> getShowByMovieName(String movieName){
        for (Theater theater : theaters) {
            for (Show show : theater.getShows()) {
                if (show.getMovie().getTitle().equals(movieName)) {
                    return Collections.singletonList(show);
                }
            }
        }
        return Collections.emptyList();
    }


}
