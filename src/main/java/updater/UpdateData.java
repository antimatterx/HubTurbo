package updater;

import util.Version;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents update data to be put on server for HT to check if there is new update
 */
public class UpdateData {

    private List<UpdateDownloadLink> listOfHTVersionsDownloadLink; // NOPMD

    public UpdateData() {
        listOfHTVersionsDownloadLink = new ArrayList<>();
    }

    /**
     * Get UpdateDownloadLink for update.
     * @return Optional.empty() if there is no update, UpdateDownloadLink of update that can be downloaded otherwise
     */
    public Optional<UpdateDownloadLink> getLatestUpdateDownloadLinkForCurrentVersion() {
        if (listOfHTVersionsDownloadLink.isEmpty()) {
            return Optional.empty();
        }

        // List the update link in descending order of version
        Collections.sort(listOfHTVersionsDownloadLink, Collections.reverseOrder());

        Version current = Version.getCurrentVersion();

        // Get link of version that has same major version or just 1 major version up than current
        Optional<UpdateDownloadLink> updateLink = listOfHTVersionsDownloadLink.stream()
                .filter(link ->
                        link.getVersion().getMajor() == current.getMajor() ||
                        link.getVersion().getMajor() == current.getMajor() + 1)
                .findFirst();

        if (updateLink.isPresent()) {
            return updateLink;
        }

        return Optional.empty();
    }
}
