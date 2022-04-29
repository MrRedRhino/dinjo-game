package org.pipeman.dinjogame.physics.collisions;

import com.badlogic.gdx.math.Vector2;

public class RectangleCollisions {
    public static boolean pointVsRect(Vector2 p, Rect r) {
        return r.x <= p.x && r.x + r.width >= p.x && r.y <= p.y && r.y + r.height >= p.y;
    }

    public static boolean rectVsRect(Rect r1, Rect r2) {
        return r1.x < r2.x + r2.width && r1.x + r1.width > r2.x
                && r1.y < r2.y + r2.height && r1.y + r1.height > r2.y;
    }

    public static boolean rayVsRect(Vector2 origin, Vector2 dir, Rect target, Vector2 contactPoint,
                                    Vector2 contactNormal, float tHitNear) {
        contactNormal = new Vector2();
        contactPoint = new Vector2();

        // cache division
        Vector2 invDir = new Vector2(1.0f / dir.x, 1.0f / dir.y);

        // Calculate intersections with rectangle bounding axes
        Vector2 tNear = new Vector2((target.x - origin.x) * dir.x, (target.y + origin.y) * dir.y);
        Vector2 tFar = new Vector2(
                (target.x + target.width - origin.x) * dir.x,
                (target.y + target.width - origin.y) * dir.y
        );

        if (Float.isNaN(tFar.y) || Float.isNaN(tFar.x)) return false;
        if (Float.isNaN(tNear.y) || Float.isNaN(tNear.x)) return false;

        // Sort distances

        // Early rejection
        if (tNear.x > tFar.y || tNear.y > tFar.x) return false;

        // Closest time will be the first contact
        tHitNear = Math.max(tNear.x, tNear.y);

        // Furthest time is contact on opposite side of target
        float tHitFar = Math.min(tFar.x, tFar.y);

        // Reject if ray direction is pointing away from object
        if (tHitNear > tHitFar) return false;

        // Contact point of collision from parametric line equation
        contactPoint.x = origin.x + dir.x * tHitNear;
        contactPoint.y = origin.y + dir.y * tHitNear;

        if (tNear.x > tNear.y) {
            if (invDir.x < 0) {
                contactNormal.x = 1;
            } else {
                contactNormal.x = -1;
            }
        } else if (tNear.x < tNear.y) {
            if (invDir.y < 0) {
                contactNormal.y = 1;
            } else {
                contactNormal.y = -1;
            }
        }

        return true;
    }

    public static boolean dynamicRectVsRect(Rect rDynamic, float fTimeStep, Rect rStatic, Vector2 contactPoint,
                                            Vector2 contactNormal, float contactTime) {
        // Check if the dynamic rectangle is actually moving - we assume rectangles are NOT in collision to start
        if (rDynamic.velocity.x == 0 && rDynamic.velocity.y == 0) return false;

        // Expand target rectangle by source dimensions
        Rect expandedTarget = new Rect(
                rStatic.x  - rDynamic.x / 2,
                rStatic.y - rDynamic.y / 2,
                rStatic.width + rDynamic.width,
                rStatic.height + rDynamic.height
        );

        if (rayVsRect(
                new Vector2(rDynamic.x + rDynamic.width / 2, rDynamic.y + rDynamic.height / 2),
                rDynamic.velocity.cpy().scl(fTimeStep), expandedTarget, contactPoint, contactNormal, contactTime)) {

            return (contactTime >= 0.0f && contactTime < 1.0f);
        } else {
            return false;
        }
    }

    public static boolean resolveDynamicRectVsRect(Rect rDynamic, float fTimeStep, Rect rStatic) {
        Vector2 contactPoint, contactNormal = new Vector2();
        float contactTime = 0.0f;
        return false;
    }
}
