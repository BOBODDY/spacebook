# Spacebook

The Spacebook application for FIXD.

### Included Features
* Login
* View feed for currently logged in user
* View posts

### Missing features
* Error handling
* Managing comments
* Loading states

### Technologies Used
* [Retrofit](https://square.github.io/retrofit/)
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android?authuser=1)
* [Navigation Component](https://developer.android.com/guide/navigation)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)

## Things I Would Do Differently

### Hilt vs Dagger
I initially chose Hilt, despite not working much with it before, for its supposed ease of setup. Any extra time was lost in having to read the documentation and debug the setup. I should've stuck with Dagger which I've used before and have example code I can use as a framework.

### Features vs Polish
My typical approach to a problem is to get a basic version working as soon as possible and iterating on it. I like to work iteratively because I have a better idea of how all of the parts work together. I should have really polished each feature of the app before trying to move on. You may notice from my commits I started doing more focused feature work towards the end as I realized that was the best way forward.