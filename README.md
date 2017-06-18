## LiveData\<T\> example

In this lecture I demonstrate how to register the app to a live stream of Location events, by using the [LiveData\<T\>](https://developer.android.com/topic/libraries/architecture/livedata.html) API from [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html).
   
Advnatages of using *LiveData\<T\>*:
- **No memory leaks**: 
  Since the Observers are bound to their own Lifecycle objects, they are automatically cleaned when their Lifecycle is destroyed.
- **No crashes due to stopped activities**: If the Observer’s Lifecycle is inactive (like an activity in the back stack), they won’t receive change events.
- **Always up to date data**: If a Lifecycle starts again (like an activity going back to started state from the back stack) it receives the latest location data (if it didn’t already).
- **Proper configuration change**: If an activity or fragment is re-created due to a configuration change (like device rotation), it instantly receives the last available Location data.
- **Sharing Resources**: Now we can keep a single instance of MyLocationListener, connect to the system service just once, and properly support all observers in the app.
- **No more manual lifecycle handling**: As you might notice, our fragment just observes the data when it wants to, does not worry about being stopped or start observing after being stopped. LiveData automatically manages all of this since the fragment provided its Lifecycle while observing.
