package freeman.rx.gxj.com.freeman.menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import freeman.rx.gxj.com.freeman.activity.R;
import freeman.rx.gxj.com.freeman.menu.animation.ExpandableSelectorAnimator;

public class ExpandableSelector extends FrameLayout {

    private static final int DEFAULT_ANIMATION_DURATION = 300;

    private List<ExpandableItem> expandableItems = Collections.EMPTY_LIST;
    private List<View> buttons = new ArrayList<View>();
    private ExpandableSelectorAnimator expandableSelectorAnimator;
    private ExpandableSelectorListener listener;
    private OnExpandableItemClickListener clickListener;

    private boolean hideBackgroundIfCollapsed;
    private Drawable expandedBackground;

    public ExpandableSelector(Context context) {
        this(context, null);
    }

    public ExpandableSelector(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandableSelector(Context context, AttributeSet attrs, int defStyleAttr,
                              int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeView(attrs);
    }

    public void showExpandableItems(List<ExpandableItem> expandableItems) {
        validateExpandableItems(expandableItems);
        reset();
        setExpandableItems(expandableItems);
        renderExpandableItems();
        hookListeners();
        bringChildsToFront(expandableItems);
    }

    public void expand() {
        expandableSelectorAnimator.expand(new ExpandableSelectorAnimator.Listener() {
            @Override
            public void onAnimationFinished() {
                notifyExpanded();
            }
        });
        notifyExpand();
        updateBackground();
    }

    public void collapse() {
        expandableSelectorAnimator.collapse(new ExpandableSelectorAnimator.Listener() {
            @Override
            public void onAnimationFinished() {
                updateBackground();
                notifyCollapsed();
            }
        });
        notifyCollapse();
    }

    /**
     * Returns true if the view is collapsed and false if the view is expanded.
     */
    public boolean isCollapsed() {
        return expandableSelectorAnimator.isCollapsed();
    }

    /**
     * Returns true if the view is expanded and false if the view is collapsed.
     */
    public boolean isExpanded() {
        return expandableSelectorAnimator.isExpanded();
    }

    /**
     * Configures a ExpandableSelectorListener instance to be notified when different collapse/expand
     * animations be performed.
     */
    public void setExpandableSelectorListener(ExpandableSelectorListener listener) {
        this.listener = listener;
    }

    /**
     * Configures a OnExpandableItemClickListener instance to be notified when a Button/ImageButton
     * inside ExpandableSelector be clicked. If the component is collapsed an the first button is
     * clicked the listener will not be notified. This listener will be notified about button clicks
     * just when ExpandableSelector be collapsed.
     */
    public void setOnExpandableItemClickListener(OnExpandableItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * Given a position passed as parameter returns the ExpandableItem associated.
     */
    public ExpandableItem getExpandableItem(int expandableItemPosition) {
        return expandableItems.get(expandableItemPosition);
    }

    /**
     * Changes the ExpandableItem associated to a given position and updates the Button widget to
     * show
     * the new ExpandableItem information.
     */
    public void updateExpandableItem(int expandableItemPosition, ExpandableItem expandableItem) {
        validateExpandableItem(expandableItem);
        expandableItems.remove(expandableItemPosition);
        expandableItems.add(expandableItemPosition, expandableItem);
        int buttonPosition = buttons.size() - 1 - expandableItemPosition;
        configureButtonContent(buttons.get(buttonPosition), expandableItem);
    }

    private void initializeView(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.expandable_selector);
        initializeAnimationDuration(attributes);
        initializeHideBackgroundIfCollapsed(attributes);
        initializeHideFirstItemOnCollapse(attributes);
        attributes.recycle();
    }

    private void initializeHideBackgroundIfCollapsed(TypedArray attributes) {
        hideBackgroundIfCollapsed = attributes.getBoolean(R.styleable.expandable_selector_hide_background_if_collapsed, false);
        expandedBackground = getBackground();
        updateBackground();
    }

    private void initializeAnimationDuration(TypedArray attributes) {
        int animationDuration = attributes.getInteger(R.styleable.expandable_selector_animation_duration, DEFAULT_ANIMATION_DURATION);
        int expandInterpolatorId = attributes.getResourceId(R.styleable.expandable_selector_expand_interpolator, android.R.anim.accelerate_interpolator);
        int collapseInterpolatorId = attributes.getResourceId(R.styleable.expandable_selector_collapse_interpolator, android.R.anim.decelerate_interpolator);
        int containerInterpolatorId = attributes.getResourceId(R.styleable.expandable_selector_container_interpolator, android.R.anim.decelerate_interpolator);
        expandableSelectorAnimator = new ExpandableSelectorAnimator(this, animationDuration, expandInterpolatorId, collapseInterpolatorId, containerInterpolatorId);
    }

    private void initializeHideFirstItemOnCollapse(TypedArray attributes) {
        boolean hideFirstItemOnCollapsed = attributes.getBoolean(R.styleable.expandable_selector_hide_first_item_on_collapse, false);
        expandableSelectorAnimator.setHideFirstItemOnCollapse(hideFirstItemOnCollapsed);
    }

    private void updateBackground() {
        if (!hideBackgroundIfCollapsed) {
            return;
        }
        if (isExpanded()) {
            setBackgroundDrawable(expandedBackground);
        } else {
            setBackgroundResource(android.R.color.transparent);
        }
    }

    private void reset() {
        this.expandableItems = Collections.EMPTY_LIST;
        for (View button : buttons) {
            removeView(button);
        }
        this.buttons = new ArrayList<View>();
        expandableSelectorAnimator.reset();
    }

    private void renderExpandableItems() {
        int numberOfItems = expandableItems.size();
        for (int i = numberOfItems - 1; i >= 0; i--) {
            View button = initializeButton(i);
            addView(button);
            buttons.add(button);
            expandableSelectorAnimator.initializeButton(button);
            configureButtonContent(button, expandableItems.get((i)));
        }
        expandableSelectorAnimator.setButtons(buttons);
    }

    private void hookListeners() {
        final int numberOfButtons = buttons.size();
        boolean thereIsMoreThanOneButton = numberOfButtons > 1;
        if (thereIsMoreThanOneButton) {
            buttons.get(numberOfButtons - 1).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCollapsed()) {
                        expand();
                    } else {
                        notifyButtonClicked(0, v);
                    }
                }
            });
        }
        for (int i = 0; i < numberOfButtons - 1; i++) {
            final int buttonPosition = i;
            buttons.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int buttonIndex = numberOfButtons - 1 - buttonPosition;
                    notifyButtonClicked(buttonIndex, v);
                }
            });
        }
    }

    private void notifyButtonClicked(int itemPosition, View button) {
        if (clickListener != null) {
            clickListener.onExpandableItemClickListener(itemPosition, button);
        }
    }

    private View initializeButton(int expandableItemPosition) {
        ExpandableItem expandableItem = expandableItems.get(expandableItemPosition);
        View button = null;
        Context context = getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (expandableItem.hasTitle()) {
            button = layoutInflater.inflate(R.layout.expandable_item_button, this, false);
        } else {
            button = layoutInflater.inflate(R.layout.expandable_item_image_button, this, false);
        }
        int visibility = expandableItemPosition == 0 ? View.VISIBLE : View.INVISIBLE;
        button.setVisibility(visibility);
        return button;
    }

    private void configureButtonContent(View button, ExpandableItem expandableItem) {
        if (expandableItem.hasBackgroundId()) {
            int backgroundId = expandableItem.getBackgroundId();
            button.setBackgroundResource(backgroundId);
        }
        if (expandableItem.hasTitle()) {
            String text = expandableItem.getTitle();
            ((Button) button).setText(text);
        }
        if (expandableItem.hasResourceId()) {
            ImageButton imageButton = (ImageButton) button;
            int resourceId = expandableItem.getResourceId();
            imageButton.setImageResource(resourceId);
        }
    }

    private void notifyExpand() {
        if (hasListenerConfigured()) {
            listener.onExpand();
        }
    }

    private void notifyCollapse() {
        if (hasListenerConfigured()) {
            listener.onCollapse();
        }
    }

    private void notifyExpanded() {
        if (hasListenerConfigured()) {
            listener.onExpanded();
        }
    }

    private void notifyCollapsed() {
        if (hasListenerConfigured()) {
            listener.onCollapsed();
        }
    }

    private boolean hasListenerConfigured() {
        return listener != null;
    }

    private void validateExpandableItem(ExpandableItem expandableItem) {
        if (expandableItem == null) {
            throw new IllegalArgumentException(
                    "You can't use a null instance of ExpandableItem as parameter.");
        }
    }

    private void validateExpandableItems(List<ExpandableItem> expandableItems) {
        if (expandableItems == null) {
            throw new IllegalArgumentException(
                    "The List<ExpandableItem> passed as argument can't be null");
        }
    }

    private void setExpandableItems(List<ExpandableItem> expandableItems) {
        this.expandableItems = new ArrayList<ExpandableItem>(expandableItems);
    }

    private void bringChildsToFront(List<ExpandableItem> expandableItems) {
        int childCount = getChildCount();
        int numberOfExpandableItems = expandableItems.size();
        if (childCount > numberOfExpandableItems) {
            for (int i = 0; i < childCount - numberOfExpandableItems; i++) {
                getChildAt(i).bringToFront();
            }
        }
    }
}
