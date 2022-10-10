package grapp.grapp;

import org.springframework.web.multipart.MultipartFile;

public class formulario {
    //@Size(min=2,max=10)
    private String text;
    private MultipartFile  img;
    
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public MultipartFile  getImg() {
        return img;
    }
    public void setImg(MultipartFile  img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Formulario [img=" + img + ", text=" + text + "]";
    }    
    
}

