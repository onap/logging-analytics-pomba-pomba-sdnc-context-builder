package org.onap.pomba.contextbuilder.sdnc.test;
/*
 * ============LICENSE_START===================================================
 * Copyright (c) 2018 Amdocs
 * ============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=====================================================
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.onap.pomba.contextbuilder.sdnc.model.Image;

public class ImageTest {
    @Test
    public void testImageWithParameters() {
        Image image = new Image("imageName");
        assertEquals("imageName", image.getImageName());
    }

    @Test
    public void testImage() {
        Image image = new Image();
        image.setImageName("imageName");
        assertEquals("imageName", image.getImageName());
        String imageString = image.toString();
        assertTrue(imageString.contains("imageName"));
    }

    @Test
    public void testImageIsEqual() {
        Image image1 = new Image("imageName");
        assertTrue(image1.equals(image1));
        assertTrue(image1.hashCode() == -878349659);
    }

}
