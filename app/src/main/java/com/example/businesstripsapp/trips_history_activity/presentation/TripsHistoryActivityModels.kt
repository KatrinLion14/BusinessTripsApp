package com.example.businesstripsapp.trips_history_activity.presentation

<<<<<<< HEAD
import com.example.businesstripsapp.trips_history_activity.models.Trip
=======
import com.example.businesstripsapp.trips_activity.models.Trip
>>>>>>> 58b2603 (add activities and layouts)

data class State(
    val loading: Boolean = false
)

sealed class Effect {
    object BackToTripsActivity : Effect()
<<<<<<< HEAD
    object ShowLoadingError : Effect()
=======
>>>>>>> 58b2603 (add activities and layouts)
    data class ToTripInformationActivity(val tripId: String): Effect()
    data class ShowAllTrips(val tripsArray: Array<Trip>) : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        object ClickBack : Ui()
        data class ClickTrip(val tripId: String) : Ui()
<<<<<<< HEAD
        data class ShowTripList(val userId: String) : Ui()
=======
>>>>>>> 58b2603 (add activities and layouts)
    }

    sealed class Internal : Event() {
        data class MakeTripList(val tripsArray: Array<Trip>) : Internal()
<<<<<<< HEAD
        object ErrorLoading : Internal()
=======
>>>>>>> 58b2603 (add activities and layouts)
    }
}

sealed class Command {
<<<<<<< HEAD
    data class LoadAllTrips(val userId: String): Command()
=======

>>>>>>> 58b2603 (add activities and layouts)
}