///////////////////////////////////////////////////////////////////////////////
//
// RealWear Development Software, Source Code and Object Code
// (c) RealWear, Inc. All rights reserved.
//
// Contact info@realwear.com for further information about the use of this
// code.
//
///////////////////////////////////////////////////////////////////////////////

using System;
using UnityEditor;

/// <summary>
/// Builder script for building APK from the command line
/// </summary>
public class Builder
{
    /// <summary>
    /// Build the project to an APK
    /// </summary>
    public static void Build()
    {
        string[] scenes = { "Assets/Main Scene.unity" };

        string[] cmds = Environment.GetCommandLineArgs();
        string buildLocation = cmds[cmds.Length - 1];

        BuildPipeline.BuildPlayer(
            scenes, buildLocation, BuildTarget.Android, BuildOptions.None);
    }
}
