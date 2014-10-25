package lt.martynassateika.cs313mobilerobotics;

import lt.martynassateika.cs313mobilerobotics.filters.ImageFilter2D;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;

/**
 * User: Martynas Sateika
 * Date: 14.2.8
 * Time: 03.38
 */
public class Controller {

    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.init();
        this.initialiseListeners();
    }

    private void init() {
        view.setSize(
            model.getSourceImage().getWidth() * 2,
            model.getSourceImage().getHeight()
        );
        this.model.getFilters().stream().forEach(view.getImageFilterComboBox()::addItem);
    }

    private void initialiseListeners() {
        view.getImageFilterComboBox().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ImageFilter2D filter = (ImageFilter2D) e.getItem();

                new SwingWorker<BufferedImage, Void>() {
                    @Override
                    protected BufferedImage doInBackground() throws Exception {
                        return filter.applyFilter(model.getSourceImage());
                    }

                    @Override
                    protected void done() {
                        super.done();
                        try {
                            model.setFilteredImage(get());
                        } catch (InterruptedException | ExecutionException e1) {
                            e1.printStackTrace();
                        }
                        view.refresh(model.getSourceImage(), model.getFilteredImage());
                    }
                }.execute();
            }
        });
    }

}
