package tests;

import org.junit.Test;
import prefs.UpdateConfig;
import util.Version;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class UpdateConfigTest {
    @Test
    public void updateConfigSetLastUpdateDownloadStatus_getValueByReflection_correctValue() {
        UpdateConfig updateConfig = new UpdateConfig();

        try {
            Class<?> updateConfigClass = updateConfig.getClass();

            Field lastUpdateDownloadStatusField = updateConfigClass.getDeclaredField("lastUpdateDownloadStatus");
            lastUpdateDownloadStatusField.setAccessible(true);

            updateConfig.setLastUpdateDownloadStatus(true);
            assertTrue((boolean) lastUpdateDownloadStatusField.get(updateConfig));

            updateConfig.setLastUpdateDownloadStatus(false);
            assertFalse((boolean) lastUpdateDownloadStatusField.get(updateConfig));
        } catch (NoSuchFieldException e) {
            fail("No field lastUpdateDownloadStatus.");
        } catch (IllegalAccessException e) {
            fail("Can't access field lastUpdateDownloadStatus");
        }
    }

    @Test
    public void updateConfigGetLastUpdateDownloadStatus_setValueByReflection_correctValue() {
        UpdateConfig updateConfig = new UpdateConfig();

        try {
            Class<?> updateConfigClass = updateConfig.getClass();

            Field lastUpdateDownloadStatusField = updateConfigClass.getDeclaredField("lastUpdateDownloadStatus");
            lastUpdateDownloadStatusField.setAccessible(true);

            lastUpdateDownloadStatusField.set(updateConfig, true);
            assertTrue(updateConfig.getLastUpdateDownloadStatus());

            lastUpdateDownloadStatusField.set(updateConfig, false);
            assertFalse(updateConfig.getLastUpdateDownloadStatus());
        } catch (NoSuchFieldException e) {
            fail("No field lastUpdateDownloadStatus.");
        } catch (IllegalAccessException e) {
            fail("Can't access field lastUpdateDownloadStatus");
        }
    }

    @Test
    public void updateConfigAddToVersionPreviouslyDownloaded_getListByReflection_correctList() {
        UpdateConfig updateConfig = new UpdateConfig();

        Version insideOfList = new Version(1, 0, 0);
        Version outsideOfList = new Version(0, 0, 0);

        updateConfig.addToVersionPreviouslyDownloaded(insideOfList);

        try {
            Class<?> updateConfigClass = updateConfig.getClass();

            Field listOfVersionsPreviouslyDownloadedField =
                    updateConfigClass.getDeclaredField("listOfVersionsPreviouslyDownloaded");
            listOfVersionsPreviouslyDownloadedField.setAccessible(true);

            List<Version> reflectedList = (List) listOfVersionsPreviouslyDownloadedField.get(updateConfig);

            assertTrue(reflectedList.contains(insideOfList));
            assertFalse(reflectedList.contains(outsideOfList));

        } catch (NoSuchFieldException e) {
            fail("No field listOfVersionsPreviouslyDownloaded.");
        } catch (IllegalAccessException e) {
            fail("Can't access field listOfVersionsPreviouslyDownloaded");
        }
    }

    @Test
    public void updateConfigCheckIfVersionWasPreviouslyDownloaded_setListByReflection_correctResult() {
        UpdateConfig updateConfig = new UpdateConfig();

        Version versionHaveBeenDownloaded = new Version(1, 0, 0);
        Version versionNeverDownloaded = new Version(0, 0, 0);

        List<Version> manualListOfVersions = Arrays.asList(versionHaveBeenDownloaded);

        updateConfig.addToVersionPreviouslyDownloaded(versionHaveBeenDownloaded);

        try {
            Class<?> updateConfigClass = updateConfig.getClass();

            Field listOfVersionsPreviouslyDownloadedField =
                    updateConfigClass.getDeclaredField("listOfVersionsPreviouslyDownloaded");
            listOfVersionsPreviouslyDownloadedField.setAccessible(true);

            listOfVersionsPreviouslyDownloadedField.set(updateConfig, manualListOfVersions);

            assertTrue(updateConfig.checkIfVersionWasPreviouslyDownloaded(versionHaveBeenDownloaded));
            assertFalse(updateConfig.checkIfVersionWasPreviouslyDownloaded(versionNeverDownloaded));


        } catch (NoSuchFieldException e) {
            fail("No field listOfVersionsPreviouslyDownloaded.");
        } catch (IllegalAccessException e) {
            fail("Can't access field listOfVersionsPreviouslyDownloaded");
        }
    }
}
