package com.example.omar.estado_fisico;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    private GoogleApiClient mClient = null;
    private static final String TAG = MainActivity.class.getSimpleName();
    //ActividadFisica af;
    /**
     * Instancia del drawer
     */
    private DrawerLayout drawerLayout;
    /**
     * Titulo inicial del drawer
     */
    private String drawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            // Añadir carácteristicas
            setupDrawerContent(navigationView);
        }
        drawerTitle = getResources().getString(R.string.home_item);
        if (savedInstanceState == null) {
            // Seleccionar item
            selectItem(drawerTitle);
        }
        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }
        buildFitnessClient();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        String title = menuItem.getTitle().toString();
                        selectItem(title);
                        return true;
                    }
                }
        );
    }

    private void selectItem(String title) {
        // Enviar título como arguemento del fragmento
        Bundle args = new Bundle();
        args.putString(PlaceHolderFragment.ARG_SECTION_TITLE, title);
        Fragment fragment = PlaceHolderFragment.newInstance(title);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();
        drawerLayout.closeDrawers(); // Cerrar drawer
        setTitle(title); // Setear título actual
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect to the Fitness API
        Log.i(TAG, "Connecting...");
        mClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mClient.isConnected()) {
            mClient.disconnect();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mClient.isConnecting() && !mClient.isConnected()) {
                    mClient.connect();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void buildFitnessClient() {
        // Create the Google API Client
        mClient = new GoogleApiClient.Builder(this)
                //.addApi(Fitness.RECORDING_API)
                .addApi(Fitness.RECORDING_API)
                .addApi(Fitness.HISTORY_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {

                            @Override
                            public void onConnected(Bundle bundle) {
                                Log.i(TAG, "Connected!!!");
                                // Now you can make calls to the Fitness APIs.
                                // Put application specific code here
                                //invokeFitnessAPIs();
                                //af = new ActividadFisica(1.1, 119, 1663, "01h 20min 00s");
                                //Parse.initialize(getBaseContext(), YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
                                ParseUser.logInInBackground("clase_gimnasia", "12345Abcde", new LogInCallback() {
                                    public void done(ParseUser user, ParseException e) {
                                        if (user != null) {
                                            // Hooray! The user is logged in.
                                            //af.setNombre(ParseUser.getCurrentUser().getUsername());
                                            //subirActividadFisica(af);
                                            //Toast toast = Toast.makeText(this, "Enhorabuena "+usuarioActual.getUsername()+" te has conectado con total exito", Toast.LENGTH_SHORT);
                                            //toast.show();
                                        } else {
                                            // Signup failed. Look at the ParseException to see what happened.
                                            //Toast toast = Toast.makeText(this, "Comprueba usuario y contraseña, no es correcto", Toast.LENGTH_SHORT);
                                            //toast.show();
                                        }
                                    }
                                });
                                ParseUser.logOut();

                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Log.i(TAG, "Connection lost.  Cause: Network Lost.");
                                } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Log.i(TAG, "Connection lost.  Reason: Service Disconnected");
                                }
                            }
                        }
                )
                .addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            // Called whenever the API client fails to connect.
                            @Override
                            public void onConnectionFailed(ConnectionResult result) {
                                Log.i(TAG, "Connection failed. Cause: " + result.toString());
                                if (!result.hasResolution()) {
                                    // Show the localized error dialog
                                    GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(),
                                            MainActivity.this, 0).show();
                                    return;
                                }
                                // The failure has a resolution. Resolve it.
                                // Called typically when the app is not yet authorized, and an
                                // authorization dialog is displayed to the user.
                                if (!authInProgress) {
                                    try {
                                        Log.i(TAG, "Attempting to resolve failed connection");
                                        authInProgress = true;
                                        result.startResolutionForResult(MainActivity.this,
                                                REQUEST_OAUTH);
                                    } catch (IntentSender.SendIntentException e) {
                                        Log.e(TAG,
                                                "Exception while starting resolution activity", e);
                                    }
                                }
                            }
                        }
                )
                .build();
    }
    public void subirActividadFisica(ActividadFisica af){
        ParseObject ActividadFisica = new ParseObject("ActividadFisica");
        ActividadFisica.put("Nombre", af.getNombre());
        ActividadFisica.put("Distancia_Estimada", af.getDistancia_estimada());
        ActividadFisica.put("Calorias", af.getCalorias());
        ActividadFisica.put("Pasos", af.getPasos());
        ActividadFisica.put("Duracion", af.getDuracion());
        ActividadFisica.saveInBackground();
        Toast toast = Toast.makeText(this, "Actividad Fisica creada correctamente", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void invokeFitnessAPIs() {
        // 1. Subscribe to fitness data (see Recording Fitness Data)
        Fitness.RecordingApi.subscribe(mClient, DataType.TYPE_STEP_COUNT_DELTA)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            if (status.getStatusCode()
                                    == FitnessStatusCodes.SUCCESS_ALREADY_SUBSCRIBED) {
                                Log.i(TAG, "Existing subscription for activity detected.");
                                // Setting a start and end date using a range of 1 week before this moment.
                                Calendar cal = Calendar.getInstance();
                                Date now = new Date();
                                cal.setTime(now);
                                long endTime = cal.getTimeInMillis();
                                cal.add(Calendar.WEEK_OF_YEAR, -1);
                                long startTime = cal.getTimeInMillis();

                                java.text.DateFormat dateFormat = DateFormat.getDateInstance();
                                Log.i(TAG, "Range Start: " + dateFormat.format(startTime));
                                Log.i(TAG, "Range End: " + dateFormat.format(endTime));

                                DataReadRequest readRequest = new DataReadRequest.Builder()
                                        // The data request can specify multiple data types to return, effectively
                                        // combining multiple data queries into one call.
                                        // In this example, it's very unlikely that the request is for several hundred
                                        // datapoints each consisting of a few steps and a timestamp.  The more likely
                                        // scenario is wanting to see how many steps were walked per day, for 7 days.
                                        .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                                                // Analogous to a "Group By" in SQL, defines how data should be aggregated.
                                                // bucketByTime allows for a time span, whereas bucketBySession would allow
                                                // bucketing by "sessions", which would need to be defined in code.
                                        .bucketByTime(1, TimeUnit.DAYS)
                                        .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                                        .build();

                                DataReadResult dataReadResult =
                                        Fitness.HistoryApi.readData(mClient, readRequest).await(0, TimeUnit.MINUTES);

                                for (DataSet d:dataReadResult.getDataSets()){

                                    Log.i(TAG, "texto" + d.getDataSource().getName());
                                }
                            } else {
                                Log.i(TAG, "Successfully subscribed!");

                            }
                        } else {
                            Log.i(TAG, "There was a problem subscribing.");
                        }
                    }
                });


        // 2. Create a session object
        // (provide a name, identifier, description and start time)
        /*Session session = new Session.Builder()
                .setName("sesionejemplo")
                .setIdentifier("omar")
                .setDescription("carreras por el monte olarizu")
                .setStartTime(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        // optional - if your app knows what activity:
                .setActivity(FitnessActivities.RUNNING)
                .build();*/

        // 3. Invoke the Sessions API with:
        // - The Google API client object
        // - The request object
        /*PendingResult<Status> pendingResult1 =
                Fitness.SessionsApi.startSession(mClient, session);*/

    // 4. Check the result (see other examples)

    //parar la session STOP A SESSIONS
    // 1. Invoke the Sessions API with:
    // - The Google API client object
    // - The name of the session
    // - The session indentifier
        /*PendingResult<SessionStopResult> pendingResult2 =
                Fitness.SessionsApi.stopSession(mClient, session.getIdentifier());*/

    // 2. Check the result (see other examples)

    // 3. Unsubscribe from fitness data (see Recording Fitness Data)
        Fitness.RecordingApi.unsubscribe(mClient, DataType.TYPE_ACTIVITY_SAMPLE)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Log.i(TAG, "Successfully unsubscribed for data type: " + DataType.TYPE_ACTIVITY_SAMPLE);
                        } else {
                            // Subscription not removed
                            Log.i(TAG, "Failed to unsubscribe for data type: " + DataType.TYPE_ACTIVITY_SAMPLE);
                        }
                    }
                });
    }
}
