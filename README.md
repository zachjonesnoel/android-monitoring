# New Relic Android application instrumentation 

This is the android application for demoing mobile app observability. 

## Pre-Requisites: 

- Network security is setup to run non-https requests in the network_security_config.xml
- Networking is turned on for in the android manifest
- Run the application without instrumentation to solve all the the dependencies 

## Next Steps
- Follow https://docs.newrelic.com/docs/mobile-monitoring/new-relic-mobile-android/get-started/introduction-new-relic-mobile-android/ compatibility and requirements 
- Follow https://docs.newrelic.com/docs/mobile-monitoring/new-relic-mobile-android/install-configure/install-android-agent-gradle/ for instrumentation step by step

## Required Code Changes (Uncomment the following snippets)

1. In [build.gradle](app/build.gradle):
   - `apply plugin: 'newrelic'` - Applies the New Relic Gradle plugin
   - `implementation("com.newrelic.agent.android:android-agent:7.6.0")` - Adds New Relic Android agent dependency
   - `implementation 'com.newrelic.agent.android:agent-ndk:1.0.3'` - Adds New Relic NDK agent support

2. In [MainActivity.java](app/src/main/java/com/example/android_base_training/MainActivity.java):
   - Import statements: `import com.newrelic.agent.android.FeatureFlag;` and others - Imports New Relic monitoring classes
   - `NewRelic.enableFeature(FeatureFlag.NativeReporting);` - Enables native crash reporting
   - `NewRelic.enableFeature(FeatureFlag.OfflineStorage);` - Enables data storage when offline
   - `NewRelic.withApplicationToken(token).start(this.getApplicationContext());` - Initializes New Relic agent
   - Various calls like `NewRelic.noticeNetworkFailure()`, `NewRelic.crashNow()`, etc. - Tracks specific events

3. In [BreadcrumbPanelActivity.java](app/src/main/java/com/example/android_base_training/BreadcrumbPanelActivity.java):
   - `import com.newrelic.agent.android.NewRelic;` - Imports New Relic monitoring
   - `NewRelic.recordBreadcrumb()` calls - Records user journey breadcrumbs

4. Add your application token
   - Replace `<YOUR TOKEN>` with your actual New Relic application token

## Running the Application
- Open it in Android Studio
- Run it in the simulator
- Resolve any issues causing it to fail
- Use the buttons in the app to generate monitoring data for New Relic


