Feature:
  User transforms file

  Background:
    Given the user has an application "Transformer" that has a command line interface
    And "Transformer" has a flag "--input" that takes a single argument that is a filename
    And "Transformer" has a flag "--output" that takes a single argument that is a filename

  @wip
  Scenario Outline: User transforms a file with users from an input file to an output file
    Given the user has a file "<input-file>"
    And the data is formatted correctly
    When the user transforms the file to "<output-file>"
    Then the exitcode from the transformer is 0
    And they see that the data is transformed to "<output-format>"
    And that the elements in "users" section of the file are ordered by element "sequence"

    Examples:
      | input-file | output-file            | output-format |
      | users.json | transformed_users.json | json          |
      | users.xml  | transformed_users.xml  | xml           |
      | users.json | transformed_users.xml  | xml           |
      | users.xml  | transformed_users.json | json          |

  @wip
  Scenario: User attempts to transform badly formatted json file
    Given the user has a file "badly_formatted.json"
    When the user transforms the file to "transformed_users.json"
    Then the exitcode from the transformer is 1
    And they see an error message telling them that the input file is badly formatted
