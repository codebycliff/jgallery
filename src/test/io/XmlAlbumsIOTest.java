//package test.io;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import io.XmlAlbumsReader;
//import io.XmlAlbumsWriter;
//import java.io.File;
//import model.AlbumModel;
//import model.GalleryModel;
//import model.PhotoModel;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import common.IAlbumModel;
//import common.IGalleryModel;
//import common.IPhotoModel;
//
//
//public class XmlAlbumsIOTest {
//
//    @Before
//    public void setUp() throws Exception {
//        IAlbumModel createdAlbumOne = new AlbumModel("Album One");
//        createdAlbumOne.setDescription("The first album");
//        IPhotoModel firstPhoto = new PhotoModel();
//        firstPhoto.setName("First Photo");
//        firstPhoto.setDescription("The first photo in the first album");
//        firstPhoto.setPath("src/runtime/resources/images/Abstract/Abstract-1.jpg");
//        IPhotoModel secondPhoto = new PhotoModel();
//        secondPhoto.setName("Second Photo");
//        secondPhoto.setDescription("The second photo in the first album");
//        secondPhoto.setPath("src/runtime/resources/images/Abstract/Abstract-2.jpg");
//        createdAlbumOne.addPhoto(firstPhoto);
//        createdAlbumOne.addPhoto(secondPhoto);
//        
//        
//        IAlbumModel createdAlbumTwo = new AlbumModel("Album Two");
//        createdAlbumTwo.setDescription("The second album");
//        IPhotoModel thirdPhoto = new PhotoModel();
//        thirdPhoto.setName("Third Photo");
//        thirdPhoto.setDescription("The first photo in the second album");
//        thirdPhoto.setPath("src/runtime/resources/images/Abstract/Abstract-3.jpg");
//        IPhotoModel fourthPhoto = new PhotoModel();
//        fourthPhoto.setName("Fourth Photo");
//        fourthPhoto.setDescription("The second photo in the second album");
//        fourthPhoto.setPath("src/runtime/resources/images/Abstract/Abstract-4.jpg");
//        createdAlbumTwo.addPhoto(thirdPhoto);
//        createdAlbumTwo.addPhoto(fourthPhoto);
//        
//        mCreatedModel = new GalleryModel();
//        mCreatedModel.addAlbum(createdAlbumOne);
//        mCreatedModel.addAlbum(createdAlbumTwo);
//        
//        mReadModel = null;
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        
//        
//        
//    }
//
//    @Test
//    public void testWrite() {
//        
//        File file = new File("XmlAlbumsIOTest.Out.xml");
//        if(file.exists()) {
//            file.delete();
//        }
//        
//        assertTrue(!file.exists());
//        
//        // Write teh file... and test that it was written
//        XmlAlbumsWriter writer = new XmlAlbumsWriter(mCreatedModel);
//        writer.write(file);
//        
//        assertTrue(file.exists());
//        
//        
//        // Read the file and test it contains the exact same content..
//        assertNull(mReadModel);
//        
//        XmlAlbumsReader reader = new XmlAlbumsReader(file);
//        reader.read();
//        mReadModel = new GalleryModel(reader.getAlbums());
//        
//        assertNotNull(mReadModel);
//        assertEquals(mCreatedModel.getCount(), mReadModel.getCount());
//        int actualIndex = 0;
//        for(IAlbumModel expected : mCreatedModel) {
//            IAlbumModel actual = mReadModel.getAlbum(actualIndex++);
//            
//            // Test the album's properties are equal
//            assertEquals(expected.getCount(), actual.getCount());
//            assertEquals(expected.getName(), actual.getName());
//            assertEquals(expected.getDescription(), actual.getDescription());
//            
//            int actualPhotoIndex = 0;
//            for(IPhotoModel expectedPhoto : expected) {
//                IPhotoModel actualPhoto = actual.getPhoto(actualPhotoIndex++);
//                
//                assertEquals(expectedPhoto.getName(), actualPhoto.getName());
//                assertEquals(expectedPhoto.getDescription(), actualPhoto.getDescription());
//                assertEquals(expectedPhoto.getPath(), actualPhoto.getPath());
//                assertEquals(expectedPhoto.getAlbum().getName(), actualPhoto.getAlbum().getName());
//                assertNotNull(actualPhoto.getMetadata());
//                assertNotNull(actualPhoto.getImage());
//                assertNotNull(actualPhoto.getIcon());
//            }
//        }
//
//        
//
//        
//        
//    }
//
//    
//    private IGalleryModel mCreatedModel;
//    private IGalleryModel mReadModel;
//    
//}
