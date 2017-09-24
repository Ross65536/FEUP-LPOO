package ros.joao.rjtorcher.gameLogic.Characters;

/**
 * Basic object fields and methods.
 */
public abstract class Entity {

    protected ros.joao.rjtorcher.Vector2D characterPosition;
    protected ros.joao.rjtorcher.Vector2D characterDimensions;

    public Entity(final ros.joao.rjtorcher.Vector2D position, final ros.joao.rjtorcher.Vector2D dimensions){
        if (position == null)
            characterPosition = new ros.joao.rjtorcher.Vector2D(0,0);
        else
            characterPosition = new ros.joao.rjtorcher.Vector2D(position);

        if (dimensions == null)
            characterDimensions= new ros.joao.rjtorcher.Vector2D(0,0);
        else
            characterDimensions = new ros.joao.rjtorcher.Vector2D(dimensions);
    }

    public double getXPos() {return characterPosition.x; }
    public double getYPos() {
        return characterPosition.y;
    }
    public double getXDim() {return characterDimensions.x; }
    public double getYDim() {return characterDimensions.y; }


    public double getYCenter()
    {
        return characterPosition.y + characterDimensions.y/2.0;
    }

    public double getXCenter() { return characterPosition.x + characterDimensions.x/2.0;};


    public boolean checkCollision(final Entity en) {
        final boolean heroXLeft = characterPosition.x + characterDimensions.x < en.characterPosition.x;
        final boolean heroXRight = characterPosition.x > en.characterPosition.x + en.characterDimensions.x;
        final boolean heroYDown = characterPosition.y + characterDimensions.y < en.characterPosition.y;
        final boolean heroYUp = characterPosition.y > en.characterPosition.y + en.characterDimensions.y;

        return !(heroXLeft || heroXRight || heroYUp || heroYDown);
    }

    public void setYPos(double YPos) {
        this.characterPosition.y = YPos;
    }
    public void setXPos(final double XPos) {
        this.characterPosition.x = XPos;
    }
}
