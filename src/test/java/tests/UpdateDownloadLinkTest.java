package tests;

import org.junit.Test;
import updater.UpdateDownloadLink;
import util.Version;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class UpdateDownloadLinkTest {

    private static final String MALFORMED_URL_MESSAGE = "URL is not well formed.";

    @Test
    public void updateDownloadLinkCompareTo_sameVersionDiffLocation_sameObject() {
        Version version = new Version(1, 0, 0);
        UpdateDownloadLink a = new UpdateDownloadLink();
        a.setVersion(version);
        try {
            a.setApplicationFileLocation(new URL("http://google.com"));
        } catch (MalformedURLException e) {
            fail(MALFORMED_URL_MESSAGE);
        }

        UpdateDownloadLink b = new UpdateDownloadLink();
        b.setVersion(version);
        try {
            b.setApplicationFileLocation(new URL("http://yahoo.com"));
        } catch (MalformedURLException e) {
            fail(MALFORMED_URL_MESSAGE);
        }

        assertTrue(a.compareTo(b) == 0);
        assertTrue(a.equals(b));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void updateDownloadLinkCompareTo_diffVersionSameLocation_diffObject() {
        String fileLocationString = "http://google.com";
        UpdateDownloadLink a = new UpdateDownloadLink();
        a.setVersion(new Version(2, 0, 0));
        try {
            a.setApplicationFileLocation(new URL(fileLocationString));
        } catch (MalformedURLException e) {
            fail(MALFORMED_URL_MESSAGE);
        }

        UpdateDownloadLink b = new UpdateDownloadLink();
        b.setVersion(new Version(1, 2, 0));
        try {
            b.setApplicationFileLocation(new URL(fileLocationString));
        } catch (MalformedURLException e) {
            fail(MALFORMED_URL_MESSAGE);
        }

        assertFalse(a.compareTo(b) == 0);
        assertFalse(a.equals(b));
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void updateDownloadLinkCompareTo_diffVersion_correctComparison() {
        String fileLocationString = "http://google.com";
        UpdateDownloadLink a = new UpdateDownloadLink();
        try {
            a.setApplicationFileLocation(new URL(fileLocationString));
        } catch (MalformedURLException e) {
            fail(MALFORMED_URL_MESSAGE);
        }

        UpdateDownloadLink b = new UpdateDownloadLink();
        try {
            b.setApplicationFileLocation(new URL(fileLocationString));
        } catch (MalformedURLException e) {
            fail(MALFORMED_URL_MESSAGE);
        }

        a.setVersion(new Version(2, 0, 0));
        b.setVersion(new Version(1, 2, 0));
        assertTrue(a.compareTo(b) > 0);

        a.setVersion(new Version(1, 0, 0));
        b.setVersion(new Version(3, 0, 3));
        assertTrue(a.compareTo(b) < 0);
    }

    @Test
    public void updateDownloadLinkGetVersion_setVersionByReflection_getCorrectValue() {
        UpdateDownloadLink a = new UpdateDownloadLink();
        try {
            a.setApplicationFileLocation(new URL("http://google.com"));
        } catch (MalformedURLException e) {
            fail(MALFORMED_URL_MESSAGE);
        }

        Version version = new Version(10, 11, 12);

        try {
            Class<?> updateDownloadLinkClass = a.getClass();

            Field versionField =
                    updateDownloadLinkClass.getDeclaredField("version");
            versionField.setAccessible(true);

            versionField.set(a, version);
        } catch (NoSuchFieldException e) {
            fail("No field version.");
        } catch (IllegalAccessException e) {
            fail("Can't access field version.");
        }

        assertEquals(version, a.getVersion());
    }

    @Test
    public void updateDownloadLinkGetFileLocation_setLocationByReflection_getCorrectValue() {
        UpdateDownloadLink a = new UpdateDownloadLink();
        a.setVersion(new Version(10, 11, 12));

        URL fileLocation;
        try {
            fileLocation = new URL("http://google.com");
        } catch (MalformedURLException e) {
            fail(MALFORMED_URL_MESSAGE);
            return;
        }

        try {
            Class<?> updateDownloadLinkClass = a.getClass();

            Field applicationFileLocationField =
                    updateDownloadLinkClass.getDeclaredField("applicationFileLocation");
            applicationFileLocationField.setAccessible(true);

            applicationFileLocationField.set(a, fileLocation);
        } catch (NoSuchFieldException e) {
            fail("No field applicationFileLocation.");
        } catch (IllegalAccessException e) {
            fail("Can't access field applicationFileLocation.");
        }

        assertEquals(fileLocation, a.getApplicationFileLocation());
    }

    @Test
    public void updateDownloadLinkSetVersion_getVersionByReflection_valueSetCorrectly() {
        UpdateDownloadLink a = new UpdateDownloadLink();
        try {
            a.setApplicationFileLocation(new URL("http://google.com"));
        } catch (MalformedURLException e) {
            fail(MALFORMED_URL_MESSAGE);
        }

        Version version = new Version(10, 11, 12);
        a.setVersion(version);

        try {
            Class<?> updateDownloadLinkClass = a.getClass();

            Field versionField =
                    updateDownloadLinkClass.getDeclaredField("version");
            versionField.setAccessible(true);

            Version versionFromReflection = (Version) versionField.get(a);
            assertEquals(version, versionFromReflection);
        } catch (NoSuchFieldException e) {
            fail("No field version.");
        } catch (IllegalAccessException e) {
            fail("Can't access field version.");
        }
    }

    @Test
    public void updateDownloadLinkSetFileLocation_getFileLocationByReflection_valueSetCorrectly() {
        UpdateDownloadLink a = new UpdateDownloadLink();
        a.setVersion(new Version(10, 11, 12));

        URL fileLocation;
        try {
            fileLocation = new URL("http://google.com");
        } catch (MalformedURLException e) {
            fail(MALFORMED_URL_MESSAGE);
            return;
        }

        a.setApplicationFileLocation(fileLocation);

        try {
            Class<?> updateDownloadLinkClass = a.getClass();

            Field applicationFileLocationField =
                    updateDownloadLinkClass.getDeclaredField("applicationFileLocation");
            applicationFileLocationField.setAccessible(true);

            URL fileLocationFromReflection = (URL) applicationFileLocationField.get(a);
            assertEquals(fileLocation, fileLocationFromReflection);
        } catch (NoSuchFieldException e) {
            fail("No field applicationFileLocation.");
        } catch (IllegalAccessException e) {
            fail("Can't access field applicationFileLocation.");
        }
    }
}
