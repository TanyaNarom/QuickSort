import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Main {
    private static JFrame jFrame = new JFrame();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(IntroScreen::new);
    }

   public static class IntroScreen {
        private static final String INTRO_PAGE = "Intro page";
        private static final String NUMBERS_TO_DISPLAY_LABEL = "How many numbers to display";
        private JTextField numberInput;
        private JButton submitButton;
        private static final String ENTER_BUTTON = "Enter";
        private static final int TEXTFIELD_SIZE = 10;

        IntroScreen() {
            jFrame.setBounds(200, 200, 500, 500);
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            createIntroScreen();
            satisfySortScreen();
        }

        private void createIntroScreen() {
            jFrame.setTitle(INTRO_PAGE);
            Container container = jFrame.getContentPane();
            container.setLayout(new GridBagLayout());
            JLabel label = new JLabel(NUMBERS_TO_DISPLAY_LABEL);
            numberInput = new JTextField(TEXTFIELD_SIZE);
            submitButton = new JButton(ENTER_BUTTON);
            submitButton.setBackground(Color.BLUE);
            submitButton.setForeground(Color.WHITE);
            submitButton.setPreferredSize(numberInput.getPreferredSize());

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.insets = new Insets(10, 0, 10, 0);
            constraints.gridy = 0;
            container.add(label, constraints);
            constraints.gridy++;
            container.add(numberInput, constraints);
            constraints.gridy++;
            container.add(submitButton, constraints);
            jFrame.setVisible(true);
        }

        private void satisfySortScreen() {
            submitButton.addActionListener(e -> {
                int amountNumbersToShow = Integer.parseInt(numberInput.getText());
                if (amountNumbersToShow > 0 && amountNumbersToShow <= 1000) {
                    jFrame.getContentPane().removeAll();
                    jFrame.getContentPane().repaint();
                    new QuickSort(amountNumbersToShow);
                }
            });
        }
    }

     public static class QuickSort {
        public static final String QUICK_SORT_PAGE_TITLE = "Sort page";
        private static boolean sortCollection = true;
        private List<Integer> numbersToShow;
        private Integer numberOfButtons;

        QuickSort(int NumbersToDisplay) {
            numberOfButtons = NumbersToDisplay;
            numbersToShow = new Random()
                    .ints(numberOfButtons, 1, 1001)
                    .collect(ArrayList::new,List::add, List::addAll);
            numbersToShow.set(new Random().nextInt(numbersToShow.size()),
                    new Random().nextInt(30) + 1);
            createSortScreen();
        }

        private void createSortScreen() {
            Container container = jFrame.getContentPane();
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            createNumbersContainer(container, gridBagConstraints);
            mouldControlButtons(container, gridBagConstraints);
            jFrame.setVisible(true);
        }

        private void createNumbersContainer(Container container, GridBagConstraints constraints) {
            constraints.weightx = 0.1;
            constraints.insets = new Insets(2, 0, 2, 0);
            jFrame.setTitle(QUICK_SORT_PAGE_TITLE);

            int column = 0;
            for (int button = 0; button < numberOfButtons; button++) {
                JButton jButton = new JButton(String.valueOf(numbersToShow.get(button)));
                jButton.addActionListener(createNewColumnNumbers(numbersToShow.get(button)));
                jButton.setBackground(Color.BLUE);
                jButton.setForeground(Color.WHITE);
                jButton.setPreferredSize(new Dimension(60, 30));

                constraints.gridy++;
                constraints.gridx = column;

                if (button % 10 == 9) {
                    constraints.gridy = -1;
                    column++;
                }
                container.add(jButton, constraints);
            }
        }

        private void mouldControlButtons(Container container, GridBagConstraints constraints) {
            constraints.gridy = 0;
            constraints.gridx++;
            JButton sortButton = new JButton("Sort");
            sortButton.setBackground(Color.GREEN);
            sortButton.setForeground(Color.WHITE);
            sortButton.setPreferredSize(new Dimension(80, 30));

            JButton returnButton = new JButton("Return");
            returnButton.setBackground(Color.GREEN);
            returnButton.setForeground(Color.WHITE);
            returnButton.setPreferredSize(new Dimension(80, 30));

            sortButton.addActionListener(sortNumbers());
            returnButton.addActionListener(returnToIntroScreen());
            container.add(sortButton, constraints);
            constraints.gridy++;
            container.add(returnButton, constraints);
        }

        private ActionListener createNewColumnNumbers(Integer number) {
            return e -> {
                if (number > 30) {
                    JOptionPane.showMessageDialog(jFrame, "Number " + number + " is bigger than " + "30");
                } else {
                    jFrame.getContentPane().removeAll();
                    jFrame.getContentPane().repaint();
                    new QuickSort(number);
                }
            };
        }

        private ActionListener sortNumbers() {
            return e -> {
                if (sortCollection) {
                    Collections.sort(numbersToShow);
                    sortCollection = false;
                } else {
                    numbersToShow.sort(Collections.reverseOrder());
                    sortCollection = true;
                }
                new QuickSort(numberOfButtons, numbersToShow).updatePage();
            };
        }

        private ActionListener returnToIntroScreen() {
            return e -> {
                jFrame.getContentPane().removeAll();
                jFrame.getContentPane().repaint();
                new IntroScreen();
            };
        }

        private void updatePage() {
            jFrame.getContentPane().removeAll();
            jFrame.getContentPane().repaint();
            new QuickSort(numberOfButtons, numbersToShow);
        }

        public QuickSort(Integer numberOfButtons, List<Integer> numbers) {
            this.numbersToShow = numbers;
            this.numberOfButtons = numberOfButtons;
            createSortScreen();
        }
    }
}
