package updater;

import util.Version;

import java.net.URL;

/**
 * Represents a HT version with its download link
 */
public class UpdateDownloadLink implements Comparable<UpdateDownloadLink> {
    private Version version;
    private URL applicationFileLocation;

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public URL getApplicationFileLocation() {
        return applicationFileLocation;
    }

    public void setApplicationFileLocation(URL applicationFileLocation) {
        this.applicationFileLocation = applicationFileLocation;
    }

    @Override
    public int compareTo(UpdateDownloadLink other) {
        return this.version.compareTo(other.version);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UpdateDownloadLink)) {
            return false;
        }
        final UpdateDownloadLink other = (UpdateDownloadLink) obj;

        return this.version.equals(other.version);
    }

    @Override
    public int hashCode() {
        return this.version.hashCode();
    }
}
