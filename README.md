# scoreboard

- A scoreboard aggregates game scores.
- A game contains two teams: home and away and their respective scores. When represented as a text, first team is the home team, second team is the away team, e.g. "Poland vs Bulgaria". The home/away order matters.
- For simplicity, the teams are represented by strings and scores by integers. There cannot be a negative score.
- Game contains lastUpdate timestamp.
- There cannot be two teams with the same name in the scoreboard. For simplicity, there is no other team name validation (e.g. regex).
- The scoreboard supports the following operations:
- Start a new game, given the names of the home and away teams. The initial score is 0 - 0.
- Finish the game, removing it from the scoreboard.
- Update the score of an ongoing game, given the names of the home and away teams and their scores.
- Get a summary of the games in progress, ordered by their total score. In case of a tie, the most recently started game should appear first.