package com.simtechdata.build;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CheckBox {


    public CheckBox(Image imgChecked, Image imgUnChecked, double size) {
        this.imgChecked = imgChecked;
        this.imgUnChecked = imgUnChecked;
        this.iv = new ImageView();
        if (selected.getValue().equals(true)) {
            this.iv.setImage(this.imgChecked);
        }
        else {
            this.iv.setImage(this.imgUnChecked);
        }
        setActions(size);
    }

    private final BooleanProperty selected = new SimpleBooleanProperty(true);
    private final Image imgChecked;
    private final Image imgUnChecked;
    private final ImageView iv;

    private void setActions(double size) {
        iv.setPreserveRatio(true);
        iv.setFitWidth(size);
        iv.setOnMouseClicked(e -> {
            boolean sValue = selected.getValue();
            boolean nValue = !sValue;
            selected.setValue(nValue);
            if (nValue) {
                iv.setImage(imgChecked);
            }
            else {
                iv.setImage(imgUnChecked);
            }
        });
    }

    public void setOnAction(ChangeListener<? super Boolean> listener) {
        selected.addListener(listener);
    }

    public ImageView getIv() {
        return iv;
    }

    public void reset() {
        selected.setValue(true);
        this.iv.setImage(this.imgChecked);
    }
}
