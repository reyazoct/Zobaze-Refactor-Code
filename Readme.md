# Zobaze Refactoring Task

## Changes

- Create view model class and transfer business logic to view model to follow MVVM pattern
- Add `Hilt` dependency to attach different compoenents of project together for hassle free experience and also to maintain `singleton` principle for repository and api class also single view model per activity or view.
- Enabled view binding in xml to manage references of each view inside my viewgroup.
- Enabled data binding to listen connect my view model flows directly into xml and update ui as Flow state changes
- Introduced Retrofit and shifted HttpURLConnection to refrofit to better manage my network calls and serialization using gson
- Used `ViewModelScope` for coroutine to make api calls using `Dispatcher.IO` to optimize asynchronous operations and cancel operation if activity lifecycle changes before asynchronous task completion
- Added `error_layout.xml` to show error if any error occurs while fetching data from server.
- Add comments whereever is requred in code to provide better code quality.