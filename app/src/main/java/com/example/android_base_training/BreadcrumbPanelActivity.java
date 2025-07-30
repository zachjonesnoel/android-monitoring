package com.example.android_base_training;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;

//Uncomment New Relic imports
//import com.newrelic.agent.android.NewRelic;

public class BreadcrumbPanelActivity extends AppCompatActivity {

    private Button addBreadcrumbButton, viewJourneyButton, clearBreadcrumbsButton, exportBreadcrumbsButton, closePanelButton;
    private TextView breadcrumbStatusText, breadcrumbInfoText;
    private StringBuilder statusLog;
    private int breadcrumbCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breadcrumb_panel);

        // Initialize views
        addBreadcrumbButton = findViewById(R.id.addBreadcrumbButton);
        viewJourneyButton = findViewById(R.id.viewJourneyButton);
        clearBreadcrumbsButton = findViewById(R.id.clearBreadcrumbsButton);
        exportBreadcrumbsButton = findViewById(R.id.exportBreadcrumbsButton);
        closePanelButton = findViewById(R.id.closePanelButton);
        breadcrumbStatusText = findViewById(R.id.breadcrumbStatusText);
        breadcrumbInfoText = findViewById(R.id.breadcrumbInfoText);

        statusLog = new StringBuilder();
        logStatus("Breadcrumb Panel opened");
        logStatus("Ready to track user journey");

        // Set up button click listeners
        addBreadcrumbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomBreadcrumb();
            }
        });

        viewJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewUserJourney();
            }
        });

        clearBreadcrumbsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllBreadcrumbs();
            }
        });

        exportBreadcrumbsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportBreadcrumbs();
            }
        });

        closePanelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeBreadcrumbPanel();
            }
        });

        // Record panel opening as breadcrumb
        Map<String, Object> panelAttributes = new HashMap<String, Object>();
        panelAttributes.put("panel", "breadcrumb_panel");
        panelAttributes.put("action", "opened");
        panelAttributes.put("timestamp", getCurrentTimestamp());

        // Uncomment New Relic breadcrumb recording
//        NewRelic.recordBreadcrumb("Panel Opened", panelAttributes);
    }

    private void addCustomBreadcrumb() {
        breadcrumbCount++;
        String breadcrumbName = "Custom Action " + breadcrumbCount;

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("action", "custom_breadcrumb");
        attributes.put("count", breadcrumbCount);
        attributes.put("panel", "breadcrumb_demo");
        attributes.put("timestamp", getCurrentTimestamp());

        // Uncomment New Relic breadcrumb recording
//        NewRelic.recordBreadcrumb(breadcrumbName, attributes);

        logStatus("✅ Added: " + breadcrumbName);
        logStatus("   → Count: " + breadcrumbCount);
    }

    private void viewUserJourney() {
        logStatus("👁️ Viewing user journey...");
//        logStatus("   → Session ID: " + NewRelic.currentSessionId());
        logStatus("   → Total breadcrumbs: " + breadcrumbCount);
        logStatus("   → Journey analysis available in New Relic dashboard");

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("action", "view_journey");
        attributes.put("breadcrumb_count", breadcrumbCount);

        // Uncomment New Relic breadcrumb recording
//        NewRelic.recordBreadcrumb("Journey Viewed", attributes);
    }

    private void clearAllBreadcrumbs() {
        logStatus("🗑️ Clearing all breadcrumbs...");
        logStatus("   → Previous count: " + breadcrumbCount);
        breadcrumbCount = 0;
        logStatus("   → Breadcrumbs cleared locally");
        logStatus("   → Note: New Relic breadcrumbs persist in session");

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("action", "clear_breadcrumbs");
        attributes.put("previous_count", breadcrumbCount);

        // Uncomment New Relic breadcrumb recording
//        NewRelic.recordBreadcrumb("Breadcrumbs Cleared", attributes);
    }

    private void exportBreadcrumbs() {
        logStatus("📤 Exporting breadcrumbs...");
        logStatus("   → Format: New Relic JSON");
//        logStatus("   → Session: " + NewRelic.currentSessionId());
        logStatus("   → Export completed");

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("action", "export_breadcrumbs");
        attributes.put("format", "json");
        attributes.put("count", breadcrumbCount);

        // Uncomment New Relic breadcrumb recording
//        NewRelic.recordBreadcrumb("Breadcrumbs Exported", attributes);
    }

    private void closeBreadcrumbPanel() {
        logStatus("❌ Closing breadcrumb panel...");

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("panel", "breadcrumb_panel");
        attributes.put("action", "closed");
        attributes.put("session_breadcrumbs", breadcrumbCount);
        attributes.put("timestamp", getCurrentTimestamp());

        // Uncomment New Relic breadcrumb recording
//        NewRelic.recordBreadcrumb("Panel Closed", attributes);

        finish(); // Close the activity and return to previous screen
    }

    private void logStatus(String message) {
        String timestamp = getCurrentTimestamp();
        statusLog.append("[").append(timestamp).append("] ").append(message).append("\n");
        breadcrumbStatusText.setText(statusLog.toString());
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("panel", "breadcrumb_panel");
        attributes.put("action", "destroyed");
        attributes.put("final_breadcrumb_count", breadcrumbCount);

        // Uncomment New Relic breadcrumb recording
//        NewRelic.recordBreadcrumb("Panel Destroyed", attributes);
    }
}