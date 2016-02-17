package tests;

import org.junit.Test;
import updater.UpdateData;
import updater.UpdateDownloadLink;
import util.Version;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class UpdateDataTest {
    @Test
    public void updateDataConstructor_noArgsConstructor_expectNoNullPointerExceptionOnMethodCall() {
        UpdateData updateData = new UpdateData();
        updateData.getLatestUpdateDownloadLinkForCurrentVersion();
    }

    /**
     * UpdateData will return download link of versions which major is greater by 1 or equal to current version,
     * regardless of minor and patch version. This is to allow migration by 1 major version difference
     */
    @Test
    public void updateDataGetDownloadLink_manuallySetListOfDownloadLink_getCorrectLink() {
        UpdateData updateData = new UpdateData();

        int currentVersionMajor = Version.getCurrentVersion().getMajor();

        // only 1 link lesser by major, will not get download link
        Version version = new Version(currentVersionMajor - 1, 10, 10);
        UpdateDownloadLink updateDownloadLink = new UpdateDownloadLink();
        updateDownloadLink.setVersion(version);
        setlistOfHTVersionsDownloadLink(updateData, Arrays.asList(updateDownloadLink));
        assertFalse(updateData.getLatestUpdateDownloadLinkForCurrentVersion().isPresent());


        // only 1 link same major lesser by minor, will get download link
        version = new Version(currentVersionMajor, Version.getCurrentVersion().getMinor() - 2, 10);
        updateDownloadLink.setVersion(version);
        setlistOfHTVersionsDownloadLink(updateData, Arrays.asList(updateDownloadLink));
        assertTrue(updateData.getLatestUpdateDownloadLinkForCurrentVersion().isPresent());

        // only 1 link same major lesser by patch, will get download link
        version = new Version(currentVersionMajor, Version.getCurrentVersion().getMinor(),
                Version.getCurrentVersion().getPatch() - 5);
        updateDownloadLink.setVersion(version);
        setlistOfHTVersionsDownloadLink(updateData, Arrays.asList(updateDownloadLink));
        assertTrue(updateData.getLatestUpdateDownloadLinkForCurrentVersion().isPresent());

        // only 1 link same major greater by minor, will get download link
        version = new Version(currentVersionMajor, Version.getCurrentVersion().getMinor() + 2, 0);
        updateDownloadLink.setVersion(version);
        setlistOfHTVersionsDownloadLink(updateData, Arrays.asList(updateDownloadLink));
        assertTrue(updateData.getLatestUpdateDownloadLinkForCurrentVersion().isPresent());

        // only 1 link greater by major, major differ by 1, will get download link
        version = new Version(currentVersionMajor + 1, 10, 10);
        updateDownloadLink.setVersion(version);
        setlistOfHTVersionsDownloadLink(updateData, Arrays.asList(updateDownloadLink));
        assertTrue(updateData.getLatestUpdateDownloadLinkForCurrentVersion().isPresent());

        // only 1 link greater by major, major differ by 2, will not get download link
        version = new Version(currentVersionMajor + 2, 10, 10);
        updateDownloadLink.setVersion(version);
        setlistOfHTVersionsDownloadLink(updateData, Arrays.asList(updateDownloadLink));
        assertFalse(updateData.getLatestUpdateDownloadLinkForCurrentVersion().isPresent());

        // 2 links, 1 same major 1 greater major differ by 2
        version = new Version(currentVersionMajor, Version.getCurrentVersion().getMinor() - 2, 10);
        updateDownloadLink.setVersion(version);
        Version anotherVersion = new Version(currentVersionMajor + 2, 10, 10);
        UpdateDownloadLink anotherUpdateDownloadLink = new UpdateDownloadLink();
        anotherUpdateDownloadLink.setVersion(anotherVersion);
        setlistOfHTVersionsDownloadLink(updateData, Arrays.asList(updateDownloadLink, anotherUpdateDownloadLink));
        assertTrue(updateData.getLatestUpdateDownloadLinkForCurrentVersion().isPresent());
        assertEquals(updateDownloadLink, updateData.getLatestUpdateDownloadLinkForCurrentVersion().get());
    }

    private void setlistOfHTVersionsDownloadLink(UpdateData updateData, List<UpdateDownloadLink> givenList) {
        try {
            Class<?> updateDataClass = updateData.getClass();

            Field listOfHTVersionsDownloadLinkField =
                    updateDataClass.getDeclaredField("listOfHTVersionsDownloadLink");
            listOfHTVersionsDownloadLinkField.setAccessible(true);

            listOfHTVersionsDownloadLinkField.set(updateData, givenList);
        } catch (NoSuchFieldException e) {
            fail("No field listOfVersionsPreviouslyDownloaded.");
        } catch (IllegalAccessException e) {
            fail("Can't access field listOfHTVersionsDownloadLink");
        }
    }
}
