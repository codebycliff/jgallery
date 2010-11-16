package test.runtime;

import static org.junit.Assert.fail;
import java.io.File;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import runtime.Application;
import runtime.Constants;


public class ApplicationRuntimeDefaultsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRuntimeDefaults() {
        fail("Not yet implemented");
    }

    @Test
    public void testBackupFilesEnabled() {
        fail("Not yet implemented");
    }

    @Test
    public void testAlbumsFile_DefaultAlbumsFile() {
        
        // Setup/Prior to test
        File existingAlbumsFile = new File(Constants.USER_ALBUMS_FILE);
        if(existingAlbumsFile.exists()) {
            if(!existingAlbumsFile.delete()) {
                fail("Problem deleting existing albums file!");
            }
        }
        
        // Start to test...
        File nonexistentAlbumsFile = new File(Constants.USER_ALBUMS_FILE);
        File packagedAlbumsFile = new File("src/runtime/" + Constants.DEFAULT_ALBUMS_FILE);
        
        Assert.assertNotNull(nonexistentAlbumsFile);
        Assert.assertNotNull(packagedAlbumsFile);
        Assert.assertTrue(!nonexistentAlbumsFile.exists());
        Assert.assertTrue(packagedAlbumsFile.exists());
        
        // Test the Application.Runtime.albumsFile()
        File albumsDefaultFile = Application.Runtime.albumsFile();
        Assert.assertNotNull(albumsDefaultFile);
        Assert.assertTrue(albumsDefaultFile.exists());
        Assert.assertEquals(albumsDefaultFile.getName(), packagedAlbumsFile.getName());
        Assert.assertTrue(albumsDefaultFile.canRead());
    }

    @Test
    public void testSettingsFile_DefaultSettingsFile() {
        
        // Setup/Prior to test
        File existingSettingsFile = new File(Constants.USER_SETTINGS_FILE);
        if(existingSettingsFile.exists()) {
            if(!existingSettingsFile.delete()) {
                fail("Problem deleting existing settings file!");
            }
        }
        
        // Start to test...
        File nonexistentUserSettings = new File(Constants.USER_SETTINGS_FILE);
        File packagedSettingsFileAsResource = new File("src/runtime/" + Constants.DEFAULT_SETTINGS_FILE);
        
        Assert.assertNotNull(nonexistentUserSettings);
        Assert.assertNotNull(packagedSettingsFileAsResource);
        Assert.assertTrue(!nonexistentUserSettings.exists());
        Assert.assertTrue(packagedSettingsFileAsResource.exists());
        
        // Test the Application.Runtime.settingsFile() method
        File applicationDefaultFile = Application.Runtime.settingsFile();
        Assert.assertNotNull(applicationDefaultFile);
        Assert.assertTrue(applicationDefaultFile.exists());
        Assert.assertEquals(packagedSettingsFileAsResource.getName(), applicationDefaultFile.getName());
        Assert.assertTrue(applicationDefaultFile.canRead());
    }

    @Test 
    public void testAlbumsFile_ExistingAlbumsFile() {
        
    }
    
    @Test
    public void testSettingsFile_ExistingSettingsFile() {
        
    }
    
    @Test
    public void testDebuggingEnabled() {
        fail("Not yet implemented");
    }

    @Test
    public void testErrorLog() {
        fail("Not yet implemented");
    }

    @Test
    public void testLookAndFeelChooser() {
        fail("Not yet implemented");
    }

    @Test
    public void testBestDisplayMode() {
        fail("Not yet implemented");
    }

}
