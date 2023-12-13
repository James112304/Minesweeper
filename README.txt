=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Recursion - recursion is used in the revealNeighbors function contained within the Minesweeper
  class. Whenever a square with 0 adjacent mines is revealed, it reveals all of its neighbors. If
  those neighbors have no adjacent mines, their neighbors are revealed... In order to prevent an
  infinite loop, a separate 2D array of booleans is stored so revealNeighbors() isn't called on a
  location it was already called on. The base case is that a tile has an adjacent mine, therefore
  revealNeighbors() isn't called again.

  2. 2D Arrays - 2D arrays are used frequently throughout the project. Used to store the tiles in
  the model of the game, Minesweeper. Specifically a Tile[][] array is used, alongside two boolean
  arrays. A Tile[][] is used so that more information can be stored than just a primitive type.

  3. File I/O - When saving the game, the current board state is essentially just converted into a
  string, using line breaks accordingly to represent columns. Enough data is included (if a tile
  is a bomb, has been revealed) to accurately recreate the state of the game on every occasion.
  IOExceptions caught.

  4. JUnit Test - the model (Minesweeper) is separate from the implementation of the game's UI. It
  was designed such that it could be tested one method at a time, also including ways to print the
  current board for testing purposes. Tests are present in ModelTest and edge case tests are included.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Minesweeper: this class serves as the model for the game, separate from the ui. A game can be made
  manually by placing mines(constructing the class with 0 mines) or automatically. It initially makes
  a 2D array of Tile objects, then replacing the Tile if it is a mine or incrementing the number of
  adjacent mines for each tile if a mine is laid next to it. The square the user initially clicks is
  protected from being a mine, alongside all adjacent squares. This class also contains the method
  used in converting the 2D array to a string for saving. It can also load the data when constructed
  with a proper String

  Tile: this class stores the data for each tile. It contains fields for number of adjacent mines, if
  it is a mine, if it has already been revealed. Once revealed, it cannot be revealed again. Initially
  constructed with 0 adjacent mines, then incremented from the Minesweeper method. Contains a method
  to access its current display state.

  GameBoard: this class is the JPanel containing the game. It is constructed with the status and
  flag counter so that they can be updated dynamically as the state on the board changes. In the
  constructor, it creates a model (30 wide by 16 long grid) with 99 mines, updating the flagCounter
  accordingly. It adds a mouseListener to the panel, then using MouseAdapter to override the
  MouseClicked method. Uses coordinates of the click to determine what tile should be changed
  accordingly. Updates status afterwards. Load and Save implementation included in this class.

  Images: this class contains all images (and a font) used in the UI. This class was created to reduce
  the number of instances in which an image would have to be loaded, saving time. Uses getter methods
  so that the stylechecker doesn't go crazy.

  RunMineSweeper: this class contains the run method for the game. It constructs a new JFrame, using
  borderLayout to assign components to their respective location. Reset, Load, Save, Instructions
  buttons all included at the top, with Instructions creating a new JFrame when clicked, the others
  affecting the GameBoard accordingly when clicked.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
Yeah, initially I tried making the Tile class extend JButton and implement the UI as such.
Ended up being a mess, also most likely wouldn't get points for a separate model and UI.
Switched to simply having the tile separate and drawing it with regular graphics.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  I would refactor if given the chance. My implementation of Minesweeper ended up confusing me
  as the coordinate system became difficult to understand as it grew more complex. In terms of
  encapsulation, I think that it's done well but could likely be improved with more getter and
  setter methods.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

Image of flag:
commons.wikimedia.org/wiki/File:Minesweeper_flag.svg

Image of bomb:
marcwerk.itch.io/minesweeper

Font:
https://fontstruct.com/fontstructions/download/1501665
