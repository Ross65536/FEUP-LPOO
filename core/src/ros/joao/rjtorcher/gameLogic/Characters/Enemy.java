package ros.joao.rjtorcher.gameLogic.Characters;

/**
 * base class that implements enemies in the game
 */
public abstract class Enemy extends Character implements ros.joao.rjtorcher.gameLogic.Characters.EnemyInfo {
    final boolean isBossType;

    /**
     * constructor for calss
     * @param position position vector
     * @param dimensions dimensions vector
     * @param speed speed vector
     * @param bossEn tells wheter this enemy is a boss type
     */
    public Enemy(ros.joao.rjtorcher.Vector2D position, ros.joao.rjtorcher.Vector2D dimensions, ros.joao.rjtorcher.Vector2D speed, boolean bossEn) {
        super(position, dimensions, speed);
        this.isBossType = bossEn;
    }

    /**
     * Modifies the movement direction of the enemy object on the X axis
     * @param bRight if true, sets the enemy movement to the right, if false sets the movement to the left
     */
    public void setMovementDirection(final boolean bRight) {
        final double xSpeed = Math.abs(this.characterSpeed.x);

        if (bRight)
            this.characterSpeed.x = xSpeed;
        else
            this.characterSpeed.x = - xSpeed;
    }

    /**
     *
     * @return true if enemy is of a boss type
     */
    @Override
    public boolean isBossType() {
        return isBossType;
    }

    /**
     *
     * @return true if enemy is of a flying type, flase is the enemy is of a ground type
     */
    abstract public boolean isFlyingType();
}
