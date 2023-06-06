export class Answer {
  static correctRegex = /\[x\] (.*)/
  static incorrectRegex = /\[ \] (.*)/
  static fullTextRegex = /= (.*)/

  id: number
  readonly text: string
  public readonly correct: boolean
  selected: boolean = false
  type: string

  private constructor (text: string, correct: boolean) {
    this.text = text
    this.correct = correct
  };

  static fromMarkdown (markdown: string): Answer {
    if (this.correctRegex.test(markdown)) {
      const [_, text] = this.correctRegex.exec(markdown)
      return new Answer(text, true)
    } else if (this.incorrectRegex.test(markdown)) {
      const [_, text] = this.incorrectRegex.exec(markdown)
      return new Answer(text, false)
    } else if (this.fullTextRegex.test(markdown)){
      const [_, text] = this.fullTextRegex.exec(markdown)
      return new Answer(text, true);
    }
  }
}
