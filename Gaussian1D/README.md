Gaussian Blur 1D
================
* Read an image file (PNG format file will be supplied)
* Separate the image into three components (red-green-blue)
* Perform a blurring operation utilizing a two pass 1D separated Gaussian kernel
 - Filter size is 1 x 15 (and thus 15 x 1)
 - Sigma is 1.6
 - Image padding is performed using reflection
* Write the resultant image to an image file (BMP, PNG, other “standard” uncompressed format)
