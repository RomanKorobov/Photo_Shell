plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    compileSdk = 33
    namespace = "com.example.photoshell"
    defaultConfig {
        minSdk = 29
        targetSdk = 33
        manifestPlaceholders["appAuthRedirectScheme"] = "com.example.photoshell"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("com.tbuonomo:dotsindicator:4.2")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")

    // reflection-based flavor
    implementation("com.github.kirich1409:viewbindingpropertydelegate:1.5.6")
    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.6")

    //viewpager
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    //Moshi


    implementation("com.squareup.moshi:moshi:1.13.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.4.0")


    //DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //circle ImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //okhttp
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlin:kotlin-android-extensions-runtime:1.5.0")

    //appauth
    implementation("net.openid:appauth:0.11.1")
    implementation("androidx.browser:browser:1.5.0")
}