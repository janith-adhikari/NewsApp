# News app

![alt text](https://github.com/[username]/[reponame]/blob/[branch]/img/home.png?raw=true)
![alt text](https://github.com/[username]/[reponame]/blob/[branch]/img/home_scrolled.png?raw=true)
![alt text](https://github.com/[username]/[reponame]/blob/[branch]/img/filter.png?raw=true)
![alt text](https://github.com/[username]/[reponame]/blob/[branch]/img/details.png?raw=true)

### Major technologies
- Language: Kotlin
- Architecture: MVVM with clean
- Android architecture components: ViewModel, LiveData, Room and Navigation
- Dependency injection: koin
- Network: Retrofit, RxJava, okHttp

#### Structure of this project with 3 layers:
- Presentation
- Domain
- Data

#### Communication between layers
1. UI calls method from ViewModel.
2. ViewModel executes Use case.
3. Use case communicate with Repositories.
4. Each Repository returns data from a Data Source (Local or Remote).
5. Information flows back to the UI where we display the list.

### The app has following packages:
1. **data**: Contains APIs, Database
2. **di**: Dependency providing classes using koin.
3. **view**: View classes along with their corresponding ViewModel.
4. **util**: Utility classes.
5. **doamin**: Contains Use Cases, and Model Objects. Business logic happens here.

### Library reference resources:
1. RxJava2: https://github.com/ReactiveX/RxJava
2. koin: https://insert-koin.io/
3. Glide: https://github.com/bumptech/glide
4. Retrofit: https://square.github.io/retrofit/
5. Room: https://developer.android.com/jetpack/androidx/releases/room
6: okhttp: https://square.github.io/okhttp/

### API
- newsapi : https://newsapi.org/docs

