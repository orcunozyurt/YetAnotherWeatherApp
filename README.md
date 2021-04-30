# YetAnotherWeatherApp
A non creative approach to Weather Apps :) 

### Architecture
- App uses MVVM design pattern
- Consists of a single activity and 2 fragments organized by Jetpack Navigation Component.
- Uses viewbinding for binding xml layout files to classes.

### 3rd Party Libraries
- `Glide` for loading images of weather icons
- `Retrofit` and `Okhttp`for networking.
- `Lottie` for a little animation work in the UI.

### TODO
- Add unit tests for `ViewModel` ```getForecast(...)```
- Add More UI Tests
- Add selection highlighting for selected elements in `RecyclerView`
- Figure out the bug for refetching forecast data each time when navigating up from details view.
- Add pull to refresh functionality.
