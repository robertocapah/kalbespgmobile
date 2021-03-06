package come.example.badge;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;


import java.util.Arrays;
import java.util.List;

import come.example.viewbadger.BadgerShortCut;
import come.example.viewbadger.ShortcutBadgeException;
import come.example.viewbadger.ShortcutBadger;

/**
 * @author Gernot Pansy
 */
public class ApexHomeBadger implements BadgerShortCut {

    private static final String INTENT_UPDATE_COUNTER = "com.anddoes.launcher.COUNTER_CHANGED";
    private static final String PACKAGENAME = "package";
    private static final String COUNT = "count";
    private static final String CLASS = "class";

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {

        Intent intent = new Intent(INTENT_UPDATE_COUNTER);
        intent.putExtra(PACKAGENAME, componentName.getPackageName());
        intent.putExtra(COUNT, badgeCount);
        intent.putExtra(CLASS, componentName.getClassName());
        context.sendBroadcast(intent);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.anddoes.launcher");
    }
}
