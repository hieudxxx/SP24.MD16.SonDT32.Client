package fpoly.md16.depotlife.Product.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class ImagesResponse {
    @SerializedName("pin_image")
    private String image;
    @SerializedName("paths")
    private String[] paths;

    public ImagesResponse(String image, String[] paths) {
        this.image = image;
        this.paths = paths;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    @Override
    public String toString() {
        return "ImagesResponse{" +
                "image='" + image + '\'' +
                ", paths=" + Arrays.toString(paths) +
                '}';
    }
}
